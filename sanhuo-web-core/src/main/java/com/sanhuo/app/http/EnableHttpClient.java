package com.sanhuo.app.http;

import com.sanhuo.app.http.HttpClientInit;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author zhangzs
 * @description
 * @date 2022/9/19 13:35
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(HttpClientInit.class)
public @interface EnableHttpClient {
}
