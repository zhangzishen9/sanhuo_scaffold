package com.sanhuo.app.http.annotation;

import com.sanhuo.app.http.invoke.resultcheck.DefaultResultCheck;
import com.sanhuo.app.http.invoke.resultcheck.HttpClientApiResultCheck;

import java.lang.annotation.*;

/**
 * @author zhangzs
 * @description 结果校验
 * @date 2022/10/8 19:58
 **/
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ResultCheck {

    Class<? extends HttpClientApiResultCheck<?>> values() default DefaultResultCheck.class;
}
