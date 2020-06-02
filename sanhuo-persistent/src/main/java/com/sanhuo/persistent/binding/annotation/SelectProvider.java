package com.sanhuo.persistent.binding.annotation;



import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface SelectProvider {
    Class<?> type();

    String method();
}
