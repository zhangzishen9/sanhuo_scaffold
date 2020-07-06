package com.sanhuo.persistent.builder.config.annotation;

import com.sanhuo.commom.basic.StringUtil;
import com.sanhuo.persistent.binding.ResultMappingHandler;
import com.sanhuo.persistent.binding.annotation.*;
import com.sanhuo.persistent.binding.property.ResultMapping;
import com.sanhuo.persistent.binding.property.SqlType;
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

    /**
     * ResultHandler
     */
    private final ResultMappingHandler resultMappingHandler;

    public MapperAnnotationBuilder(Configuration configuration, Class<?> type) {
        this.configuration = configuration;
        this.sqlSourceBuilder = new SqlSourceBuilder(this.configuration);
        this.type = type;
        this.mappedStatementBuilder = MappedStatement.builder().resource(type);
        this.resultMappingHandler = new ResultMappingHandler(this.configuration);
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
        SqlSource sqlSource = this.getSqlFromAnnotations(method, parameterMappings);
        //获取method的Result注解,构建结果映射
        ResultMapping resultMapping = resultMappingHandler.parse(method, this.type);
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
    private SqlSource getSqlFromAnnotations(Method method, List<ParameterMapping> parameterMappings) {
        //找出配置在method中的SQL指令类型注解，就是 {@link Select},{@link Insert},{@link Update},{@link Delete}
        Class<? extends Annotation> sqlAnnotationType = getSqlAnnotationType(method);
        if (sqlAnnotationType != null) {
            //有,获取该注解
            Annotation sqlAnnotation = method.getAnnotation(sqlAnnotationType);
            //同过反射的方式取出SQL指令类型注解的value方法的返回值
            final String originSql;
            try {
                originSql = (String) sqlAnnotation.getClass().getMethod("value").invoke(sqlAnnotation);
                return sqlSourceBuilder.parse(originSql, parameterMappings, null);
            } catch (Exception e) {
                //todo 处理
                e.printStackTrace();
            }

        } else {
            Class<? extends Annotation> sqlProviderAnnotationType = getSqlProviderAnnotationType(method);
            if (sqlProviderAnnotationType != null) {
                //有,获取该注解
                Annotation sqlProviderAnnotation = method.getAnnotation(sqlProviderAnnotationType);
                return sqlSourceBuilder.parse(null, parameterMappings, sqlProviderAnnotation);
            } else {
                //todo 都没有 抛出异常

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
            Annotation annotation = method.getAnnotation(type.getBasic());
            if (annotation != null) {
                //当前的sql类型
                mappedStatementBuilder.sqlType(type);
                return type.getBasic();
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
    private Class<? extends Annotation> getSqlProviderAnnotationType(Method method) {
        for (SqlType type : SqlType.class.getEnumConstants()) {
            Annotation annotation = method.getAnnotation(type.getProvider());
            if (annotation != null) {
                //当前的sql类型
                mappedStatementBuilder.sqlType(type);
                return type.getProvider();
            }
        }
        return null;
    }


}
