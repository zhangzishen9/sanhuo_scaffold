package com.sanhuo.persistent.builder.config.annotation;

import com.sanhuo.commom.utils.StringUtil;
import com.sanhuo.persistent.binding.annotation.*;
import com.sanhuo.persistent.binding.property.ColumnProperty;
import com.sanhuo.persistent.binding.property.ResultMapping;
import com.sanhuo.persistent.binding.property.SqlType;
import com.sanhuo.persistent.binding.property.TableProperty;
import com.sanhuo.persistent.builder.SqlSourceBuilder;
import com.sanhuo.persistent.mapping.MappedStatement;
import com.sanhuo.persistent.mapping.ParameterMapping;
import com.sanhuo.persistent.mapping.SqlSource;
import com.sanhuo.persistent.reflection.Reflector;
import com.sanhuo.persistent.session.Configuration;
import com.sanhuo.persistent.type.JdbcType;
import com.sanhuo.persistent.type.TypeHandler;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

/**
 * 注解方式构建mapper
 *
 * @author sanhuo
 * @date 2020/3/1 0001 下午 17:50
 */
public class MapperAnnotationBuilder {

    private static final Set<Class<? extends Annotation>> SQL_PROVIDER_ANNOTATION_TYPES = new HashSet<Class<? extends Annotation>>();

    private final Configuration configuration;
    /**
     * 要解析的目标mapper接口的Class对象
     */
    private final Class<?> type;

    /**
     * sqlSource建造器
     */
    private final SqlSourceBuilder sqlSourceBuilder;

    /**
     * 最后返回的mapperStatement
     */

    private final MappedStatement.MappedStatementBuilder mappedStatementBuilder;

    public MapperAnnotationBuilder(Configuration configuration, Class<?> type) {
        String resource = type.getName().replace('.', '/') + ".java (best guess)";
        this.configuration = configuration;
        this.sqlSourceBuilder = new SqlSourceBuilder(configuration);
        this.type = type;
        this.mappedStatementBuilder = MappedStatement.builder().resource(type);
        SQL_PROVIDER_ANNOTATION_TYPES.add(SelectProvider.class);
        SQL_PROVIDER_ANNOTATION_TYPES.add(InsertProvider.class);
        SQL_PROVIDER_ANNOTATION_TYPES.add(UpdateProvider.class);
        SQL_PROVIDER_ANNOTATION_TYPES.add(DeleteProvider.class);
    }

    /**
     * 解析类里面的
     */
    public void parse() {
        String resource = type.toString();
        if (!configuration.isResourceLoaded(resource)) {
            configuration.addLoadedResource(resource);
            //TODO 处理缓存
            Method[] methods = type.getDeclaredMethods();
            for (Method method : methods) {
                if (!method.isBridge()) {
                    parseStatement(method);
                }
            }
        }
    }

    /**
     * 解析method生成mappedStatement
     *
     * @param method
     */
    private void parseStatement(Method method) {
        //获取方法入参
        Parameter[] parameterTypes = this.getParameters(method);
        //获取method的参数,构建参数映射
        List<ParameterMapping> parameterMappings = this.parseParameterMapping(parameterTypes);
        //获取sql
        String sql = this.getSqlFromAnnotations(method);
        if (StringUtil.isBlank(sql)) {
            return;
        }
        //构建sqlSource对象
        SqlSource sqlSource = this.sqlSourceBuilder.parse(sql, parameterMappings);
        //获取method的Result注解,构建结果映射
        ResultMapping resultMapping = this.parseResultMapping(method);
        //方法的签名
        String id = Reflector.getSignature(method);
        //构建mapperstatement
        MappedStatement mappedStatement = mappedStatementBuilder.sqlSource(sqlSource)
                .id(id)
                .resultMapping(resultMapping)
                .build();
        this.configuration.addMappedStatement(id, mappedStatement);

    }

    /**
     * 解析结果映射
     *
     * @param method
     * @return
     */
    private ResultMapping parseResultMapping(Method method) {
        Results resultsAnnotation = getResultsAnnotation(method);
        ResultMapping.ResultMappingBuilder builder = ResultMapping.builder();
        if (resultsAnnotation == null) {
            //直接拿method对应的实体类作为返回值
            Class<?> returnType = method.getReturnType();
            //如果实体类和mapper解析的实体一样,直接拿mapper解析的实体对应的资料
            Class mappedEntity = this.configuration.getMappedEntity(this.type);
            if (returnType.equals(mappedEntity)) {
                TableProperty tableProperty = this.configuration.getEntityParsing(mappedEntity);
                //字段解析
                Map<String, ColumnProperty> columnPropertyMap = tableProperty.getColumns();
                List<ResultMapping.Result> results = new LinkedList<>();
                columnPropertyMap.entrySet().stream().forEach(entry -> {
                    ColumnProperty columnProperty = entry.getValue();
                    ResultMapping.Result result = ResultMapping.Result.builder()
                            .columnName(columnProperty.getColumnName())
                            .fieldName(columnProperty.getFieldName())
                            .typeHandler(columnProperty.getTypeHandler())
                            .build();
                    results.add(result);
                });
                return builder.columns(results).build();
            }
            //todo 解析对应的实体类

        }//todo 解析results注解
        return null;
    }

    /**
     * 解析参数映射
     *
     * @param parameters
     * @return
     */
    private List<ParameterMapping> parseParameterMapping(Parameter[] parameters) {
        List<ParameterMapping> parameterMappings = new LinkedList<>();
        //参数的顺序
        int index = 1;
        for (Parameter parameter : parameters) {
            //参数名
            String paramName = parameter.getName();
            //参数java类型
            Class paramType = parameter.getType();
            //参数的jdbc类型
            JdbcType jdbcType = this.configuration.getTypeParsingRegistry().getJdbcType(paramType);
            //参数转换器
            TypeHandler typeHandler = this.configuration.getTypeHandlerRegistry().getHandler(paramType);

            //判断参数上有没有@Param注解
            Param param = parameter.getAnnotation(Param.class);
            if (param != null) {
                //参数名为空则使用
                paramName = StringUtil.isBlank(param.value()) ? paramName : param.value();
                //jdbcType为NULL的话直接使用javatype的默认对应的jdbcType
                jdbcType = JdbcType.NULL.equals(param.jdbcType()) ? jdbcType : param.jdbcType();
                typeHandler = JdbcType.NULL.equals(param.jdbcType()) ? typeHandler : this.configuration.getTypeHandlerRegistry().getHandler(paramType, jdbcType);
            }
            parameterMappings.add(
                    ParameterMapping.builder()
                            .name(paramName)
                            .type(paramType)
                            .typeHandler(typeHandler)
                            .build()
            );
        }
        return parameterMappings;
    }


    /**
     * 获取方法的参数列表
     *
     * @param method
     * @return
     */
    private Parameter[] getParameters(Method method) {
        return method.getParameters();
    }


    /**
     * 从注解里获取sql
     *
     * @param method
     * @return
     */
    private String getSqlFromAnnotations(Method method) {
        //找出配置在method中的SQL指令类型注解，就是 {@link Select},{@link Insert},{@link Update},{@link Delete}
        Class<? extends Annotation> sqlAnnotationType = getSqlAnnotationType(method);
        if (sqlAnnotationType != null) {
            //如果有配置SQL provide注解

            //都没有 抛出异常

            //有,获取该注解
            Annotation sqlAnnotation = method.getAnnotation(sqlAnnotationType);
            //同过反射的方式取出SQL指令类型注解的value方法的返回值
            final String originSql;
            try {
                return (String) sqlAnnotation.getClass().getMethod("value").invoke(sqlAnnotation);
            } catch (Exception e) {
                //todo 处理
                e.printStackTrace();
            }

        }

        return null;
    }

    /**
     * 获取方法上的
     * {@link Select},
     * {@link Insert},
     * {@link Update},
     * {@link Delete},
     * 四种注解
     */
    private Class<? extends Annotation> getSqlAnnotationType(Method method) {
        for (SqlType type : SqlType.class.getEnumConstants()) {
            Annotation annotation = method.getAnnotation(type.getValue());
            if (annotation != null) {
                //当前的sql类型
                mappedStatementBuilder.sqlType(type);
                return type.getValue();
            }
        }
        return null;
    }

    /**
     * 获取provider类的注解
     *
     * @param method
     * @return
     */
    private Annotation getSqlProviderAnnotationType(Method method) {
        return null;
    }

    /**
     * 获取resultMap注解
     */
    private Results getResultsAnnotation(Method method) {
        Results result = method.getAnnotation(Results.class);
        return result != null ? result : null;
    }


}
