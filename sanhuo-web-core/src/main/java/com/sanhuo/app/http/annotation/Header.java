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

    String name();

    String value();
    
}
