package com.sanhuo.persistent.binding.annotation;

import com.sanhuo.persistent.enums.GenerationStrategy;

import java.lang.annotation.*;

/**
 * <p>
 * 表明该字段是主键
 * </p>
 *
 * @author sanhuo
 * @createTime 2020/5/12:21:39
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Identifier {

    /**
     * 主键生成的策略
     *
     * @return
     */
    GenerationStrategy strategy() default GenerationStrategy.UUID;
}
