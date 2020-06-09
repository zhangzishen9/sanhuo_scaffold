package com.sanhuo.persistent.binding;

import com.sanhuo.commom.utils.ObjectUtil;
import com.sanhuo.persistent.binding.annotation.Results;
import com.sanhuo.persistent.binding.property.ColumnProperty;
import com.sanhuo.persistent.binding.property.ResultMapping;
import com.sanhuo.persistent.binding.property.TableProperty;
import com.sanhuo.persistent.session.Configuration;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
        Results resultsAnnotation = getResultsAnnotation(method);
        ResultMapping.ResultMappingBuilder builder = ResultMapping.builder();
        if (resultsAnnotation == null) {
            //直接拿method对应的实体类作为返回值
            Class<?> returnType = method.getReturnType();
            builder.type(returnType);
            //如果实体类和mapper解析的实体一样,直接拿mapper解析的实体对应的资料
            Class mappedEntity = this.configuration.getMappedEntity(type);
            if (returnType.equals(mappedEntity)) {
                return builder.columns(this.parseMappedEntity(mappedEntity)).build();
            } else {
                //todo 解析对应的实体类
                //判断是否是八大基础类型
                if (ObjectUtil.isPrimitive(returnType)) {

                }
            }
        }
//todo 解析results注解
        return null;
    }

    /**
     * 获取resultMap注解
     */
    private Results getResultsAnnotation(Method method) {
        Results result = method.getAnnotation(Results.class);
        return result != null ? result : null;
    }

    /**
     * 方法返回值是mapper所映射的实体的处理
     *
     * @param mappedEntity
     * @return
     */
    private List<ResultMapping.Result> parseMappedEntity(Class mappedEntity) {
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
        return results;
    }

}
