package com.sanhuo.app.http.annotation;

import com.sanhuo.app.http.DefaultResultCheck;
import com.sanhuo.app.http.HttpClientApiResultCheck;

import java.lang.annotation.*;

/**
 * @author zhangzs
 * @description
 * @date 2022/9/19 13:27
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HttpClient {


    String value();

    Class<? extends HttpClientApiResultCheck> check() default DefaultResultCheck.class;
}
