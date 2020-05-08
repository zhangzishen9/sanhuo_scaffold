package com.sanhuo.persistent.mapper.annotation;

import com.sanhuo.persistent.base.SQL;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface SelectProvider {
    Class<? extends SQL> type();

    String method();
}
