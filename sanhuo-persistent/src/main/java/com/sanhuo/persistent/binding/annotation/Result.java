package com.sanhuo.persistent.binding.annotation;

import com.sanhuo.persistent.type.JdbcType;

import java.lang.annotation.*;

/**
 * <p>
 * 结果映射
 * </p>
 *
 * @author sanhuo
 * @createTime 2020/5/25:20:45
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.FIELD})
public @interface Result {
    /**
     * 对应的数据库列名
     *
     * @return
     */
    String columnName();

    /**
     * 对应的java名
     *
     * @return
     */
    String fieldName();

    /**
     * java类型
     *
     * @return
     */
    Class javaType();

    /**
     * jdbc类型
     *
     * @return
     */
    JdbcType jdbcType() default JdbcType.NULL;
}
