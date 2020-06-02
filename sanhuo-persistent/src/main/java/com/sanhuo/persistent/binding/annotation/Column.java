package com.sanhuo.persistent.binding.annotation;

import com.sanhuo.persistent.type.JdbcType;

import javax.validation.groups.Default;
import java.lang.annotation.*;

/**
 * <p>
 * 映射字段
 * </p>
 *
 * @author sanhuo
 * @createTime 2020/5/13:20:41
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Column {
    /**
     * 列名
     * @return
     */
    String value() default "";

    /**
     * 数据库类型
     * @return
     */
    JdbcType type() default JdbcType.NULL;

    /**
     * 长度
     * @return
     */
    int length() default 255;

    /**
     * 不为空
     * @return
     */
    boolean notNull() default false;

}
