package com.sanhuo.app.http.annotation;

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

    //todo 单独过期时间

}
