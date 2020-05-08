package com.sanhuo.persistent;

import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 声明sanhuo-persistent启动注解
 *
 * @author sanhuo
 * @date 2020/3/1 0001 下午 21:38
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(SanHuoPersistentInit.class)
public @interface EnableSanHuoPersistent {
    /**
     * 效果和mapperScan一致
     * @return
     */
    @AliasFor("mapperScan")
    String value() default "";

    /**
     * 扫描包名
     * @return
     */
    @AliasFor("value")
    String mapperScan() default "";
}
