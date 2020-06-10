package com.sanhuo.persistent.binding.annotation;

import java.lang.annotation.*;

/**
 * 结果映射
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface Results {

    Result[] value() default {};
}
