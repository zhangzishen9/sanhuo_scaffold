package com.sanhuo.persistent.binding.property;

import com.sanhuo.persistent.type.JdbcType;
import com.sanhuo.persistent.type.TypeHandler;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Field;

/**
 * <p>
 * 类属性的一些属性
 * </p>
 *
 * @author sanhuo
 * @createTime 2020/5/21:21:51
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ColumnProperty {
    /**
     * 类属性名
     */
    private String fieldName;
    /**
     * 对应的数据库列名
     */
    private String columnName;
    /**
     * 数据库列的类型
     */
    private JdbcType jdbcType;
    /**
     * 类属性类型
     */
    private Class<?> javaType;
    /**
     * 类型转换器
     */
    private TypeHandler<?> typeHandler;
    /**
     * true -> 是主键 ,false -> 不是主键
     */
    private Boolean primaryKey;
    /**
     * 长度
     */
    private Integer length;
    /**
     * 不为空
     */
    private Boolean notNull;
    /**
     * 对应的field
     */
    private Field field;


}
