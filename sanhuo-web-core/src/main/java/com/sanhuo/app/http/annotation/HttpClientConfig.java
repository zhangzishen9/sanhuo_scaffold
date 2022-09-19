package com.sanhuo.app.http.annotation;

import com.sanhuo.app.http.HttpClientConstant;

import java.lang.annotation.*;

/**
 * @author zhangzs
 * @description
 * @date 2022/9/19 15:11
 **/
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HttpClientConfig {

    long readTimeout() default HttpClientConstant.READ_TIMEOUT;


    long connectTimeout() default  HttpClientConstant.CONNECT_TIMEOUT;
}
