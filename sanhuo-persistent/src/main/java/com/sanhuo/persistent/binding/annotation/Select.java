package com.sanhuo.persistent.binding.annotation;

import com.sanhuo.persistent.binding.property.SqlType;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface Select {

    String value();


}
