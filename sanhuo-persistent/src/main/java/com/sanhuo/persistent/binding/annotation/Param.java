package com.sanhuo.persistent.binding.annotation;

import com.sanhuo.persistent.type.JdbcType;
import com.sanhuo.persistent.type.TypeHandler;

import java.lang.annotation.*;
import java.sql.JDBCType;

/**
 * <p>
 * 方法参数
 * </p>
 *
 * @author sanhuo
 * @createTime 2020/5/12:21:43
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Param {

    String value();


    JdbcType jdbcType() default JdbcType.NULL;


}
