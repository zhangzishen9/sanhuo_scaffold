package com.sanhuo.app.http.annotation;

import java.lang.annotation.*;

/**
 * @author zhangzs
 * @description 配置请求头
 * @date 2022/9/19 13:28
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Headers {

    Header[] values() default {};
}
