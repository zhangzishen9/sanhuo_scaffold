package com.sanhuo.persistent.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface Result {

    //传进来的实体类的字段名
    String property();

    //数据库表的字段名
    String colomn();

    Many[] many() default {};

    //对应的实体类的类型
    Class javaType() default void.class;
}
