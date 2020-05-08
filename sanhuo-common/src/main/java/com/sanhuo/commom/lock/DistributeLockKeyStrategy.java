package com.sanhuo.commom.lock;

/**
 * 分布式锁的lockkey获取策略
 *
 * @author sanhuo
 * @date 2020/1/22 11:13
 */
public enum DistributeLockKeyStrategy {
    /**
     * 直接使用方法签名
     */
    METHOD,
    /**
     * 使用用户信息 + 方法签名
     */
    USER_METHOD,
    /**
     * 自定义
     */
    CUSTOM
}
