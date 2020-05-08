package com.sanhuo.commom.lock.distribute.annotation;

import com.sanhuo.commom.lock.DistributeLockKeyStrategy;
import com.sanhuo.commom.lock.DistributeLockStrategy;

import java.lang.annotation.*;

/**
 * 分布式锁
 *
 * @author sanhuo
 * @date 2020/1/16 17:17
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DistributeLock {

    String lockKey() default "";

    /**
     * 锁的有效时间 单位秒
     */
    long timeOut() default 60;

    /**
     * 生成锁键的策略 ,默认以当前的方法签名为lockKey
     */
    DistributeLockKeyStrategy lockKeyStrategy() default DistributeLockKeyStrategy.CUSTOM;

    /**
     * 获取不到锁之后的策略,默认进行自旋重试
     */
    DistributeLockStrategy strategy() default DistributeLockStrategy.RETRY;


}
