package com.sanhuo.app.http.annotation;

import java.lang.annotation.*;

/**
 * @author zhangzs
 * @description 标记该参数用于请求头
 * @date 2022/10/8 19:57
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface HeadersParam {
}
