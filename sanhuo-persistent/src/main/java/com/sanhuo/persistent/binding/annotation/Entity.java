package com.sanhuo.persistent.binding.annotation;

import java.lang.annotation.*;

/**
 * <p>
 * 表明这是个映射到数据库的实体类
 * </p>
 *
 * @author sanhuo
 * @createTime 2020/5/12:20:21
 */
@Retention(RetentionPolicy.RUNTIME)//注解在什么范围有效,运行时有效
@Target(ElementType.TYPE)//注解可以用于的地方,Type表示类或接口或enum声明
@Documented //会生成文档
public @interface Entity {
}
