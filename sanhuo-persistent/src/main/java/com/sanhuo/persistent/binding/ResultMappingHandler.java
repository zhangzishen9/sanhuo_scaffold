package com.sanhuo.persistent.binding;

import com.sanhuo.commom.basic.ObjectUtil;
import com.sanhuo.commom.basic.StringUtil;
import com.sanhuo.persistent.binding.annotation.Result;
import com.sanhuo.persistent.binding.annotation.Results;
import com.sanhuo.persistent.binding.property.ColumnProperty;
import com.sanhuo.persistent.binding.property.ResultMapping;
import com.sanhuo.persistent.binding.property.TableProperty;
import com.sanhuo.persistent.builder.config.yml.YmlConfigBuilder;
import com.sanhuo.persistent.enums.CollectionType;
import com.sanhuo.persistent.excutor.Executor;
import com.sanhuo.persistent.reflection.Reflector;
import com.sanhuo.persistent.session.Configuration;
import com.sanhuo.persistent.type.JdbcType;
import com.sanhuo.persistent.type.TypeHandler;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.*;

/**
 * <p>
 * 结果映射解析助手
 * </p>
 *
 * @author sanhuo
 * @createTime 2020/6/9:21:33
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultMappingHandler {

    private Configuration configuration;

    /**
     * 解析方法的结果映射
     *
     * @return
     */
    public ResultMapping parse(Method method, Class type) {
        //todo 处理分页
        ResultMapping.ResultMappingBuilder builder = ResultMapping.builder();
        //返回值
        Class<?> returnType = method.getReturnType();
        //是否是列表
        boolean isCollection = this.isCollection(returnType);
        builder.isCollection(isCollection);
        //是
        if (isCollection) {
            //是List还是Set
            builder.collectionType(this.getCollectionType(returnType));
            //获取List/Set里的泛型
            returnType = this.getGenericReturnType(method);
        }
        //结果映射注解
        Results resultsAnnotation = getResultsAnnotation(method);
        //1.不为空则直接以results的为准
        if (resultsAnnotation != null && resultsAnnotation.value().length > 0) {
            builder.type(ObjectUtil.isPrimitive(returnType) ? ObjectUtil.getPackageClass(returnType) : returnType);
            return builder.columns(this.parseResultAnnotation(resultsAnnotation)).build();
        } //结果映射注解为空
        else {
            //2.判断返回的类型和mapper映射的是否一致
            //如果实体类和mapper解析的实体一样,直接拿mapper解析的实体对应的资料
            Class mappedEntity = this.configuration.getMappedEntity(type);
            if (returnType.equals(mappedEntity)) {
                builder.type(mappedEntity);
                return builder.columns(this.parseMappedEntity(mappedEntity)).build();
            }
            //3.不一致判读是否是八大基础类型
            if (ObjectUtil.isPrimitive(returnType)) {
                //获取其包装类型
                Class packageType = ObjectUtil.getPackageClass(returnType);
                builder.type(packageType);
                return builder.columns(this.parsePrimitive(packageType)).build();
            }
            //4.其他的实体类则对其进行解析
            builder.type(returnType);
            return builder.columns(this.parseObject(returnType)).build();
        }
    }

    /**
     * 获取resultMap注解
     */
    private Results getResultsAnnotation(Method method) {
        Results result = method.getAnnotation(Results.class);
        return result != null ? result : null;
    }

    /**
     * 解析@Results注解
     *
     * @param resultsAnnotation @Results注解
     * @return
     */
    private List<ResultMapping.Result> parseResultAnnotation(Results resultsAnnotation) {
        List<ResultMapping.Result> results = new LinkedList<>();
        Result[] resultAnnotations = resultsAnnotation.value();
        for (Result resultAnnotation : resultAnnotations) {
            ResultMapping.Result.ResultBuilder resultBuilder = ResultMapping.Result.builder()
                    //类字段和表列名和java类型都不能为空
                    .columnName(resultAnnotation.columnName())
                    .fieldName(resultAnnotation.fieldName());
            Class javaType = resultAnnotation.javaType();
            JdbcType jdbcType = resultAnnotation.jdbcType().equals(JdbcType.NULL) ?
                    this.configuration.getTypeParsingRegistry().getJdbcType(resultAnnotation.javaType()) : resultAnnotation.jdbcType();
            TypeHandler typeHandler = resultAnnotation.jdbcType().equals(JdbcType.NULL) ?
                    this.configuration.getTypeHandlerRegistry().getHandler(javaType) :
                    this.configuration.getTypeHandlerRegistry().getHandler(javaType, jdbcType);
            resultBuilder.typeHandler(typeHandler);
            results.add(resultBuilder.build());
        }
        return results;
    }

    /**
     * 返回值是基础类型
     *
     * @param packageType 基础类型的包装类型
     * @return
     */
    private List<ResultMapping.Result> parsePrimitive(Class packageType) {
        //结果处理器
        TypeHandler typeHandler = this.configuration.getTypeHandlerRegistry().getHandler(packageType);
        //基础类型说明只有一个字段 默认只拿第一个 (在进行具体的resultset.getXXX时再处理具体的出错可能性)
        return Collections.singletonList(new ResultMapping.Result(typeHandler, 0));
    }

    /**
     * 方法返回值是mapper所映射的实体的处理
     *
     * @param mappedEntity mapper映射的实体类
     * @return
     */
    private List<ResultMapping.Result> parseMappedEntity(Class mappedEntity) {
        TableProperty tableProperty = this.configuration.getEntityParsing(mappedEntity);
        //实体的字段解析
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
        return results;
    }

    /**
     * 解析其他对象
     *
     * @param objectClass
     * @return
     */
    private List<ResultMapping.Result> parseObject(Class objectClass) {
        List<ResultMapping.Result> results = new LinkedList<>();
        //对象的所有字段
        List<Field> fields = Reflector.getClassField(objectClass);
        //只做单层的解析 内嵌对象不做处理,请用result注解来自定义typehandler
        fields.stream().forEach(field -> {
            //java属性名
            String fieldName = field.getName();
            //表列名
            String columnName = StringUtil.uder2Camel(fieldName);
            //java类型
            Class javaType = field.getType();
            //类型转换器
            TypeHandler typeHandler = this.configuration.getTypeHandlerRegistry().getHandler(javaType);
            results.add(ResultMapping.Result.builder().columnName(columnName).fieldName(fieldName).typeHandler(typeHandler).build());
        });
        return results;
    }

    /**
     * 判断返回值是否是列表
     *
     * @return
     */
    private boolean isCollection(Class type) {
        return Collection.class.isAssignableFrom(type);
    }

    /**
     * 获取列表里的泛型
     *
     * @param method
     * @return
     */
    private Class getGenericReturnType(Method method) {
        return (Class) ParameterizedType.class.cast(method.getGenericReturnType()).getActualTypeArguments()[0];
    }


    private Class getCollectionType(Class type) {
        for (CollectionType collectionType : CollectionType.class.getEnumConstants()) {
            if (collectionType.type.equals(type.getName())) {
                return collectionType.value;
            }
        }
        return null;
    }
}
