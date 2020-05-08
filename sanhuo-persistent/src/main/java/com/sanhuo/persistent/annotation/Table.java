package com.sanhuo.persistent.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)//注解在什么范围有效,运行时有效
@Target(ElementType.TYPE)//注解可以用于的地方,Type表示类或接口或enum声明
@Documented //会生成文档
public @interface Table {

    String value();
}
