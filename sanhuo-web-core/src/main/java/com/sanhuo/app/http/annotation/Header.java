package com.sanhuo.app.http.annotation;

import java.lang.annotation.*;

/**
 * @author zhangzs
 * @description
 * @date 2022/9/19 13:29
 **/


@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Header {
    /**
     * 请求头参数名
     */
    String name() default "";

    /**
     * 请求头参数值
     */
    String value() default "";

    /**
     * 也可以直接写，例如：Content-Type=application/json;charset=UTF-8，为空时取 name = value,不为空时该值优先度较高
     */
    String values() default "";

}
