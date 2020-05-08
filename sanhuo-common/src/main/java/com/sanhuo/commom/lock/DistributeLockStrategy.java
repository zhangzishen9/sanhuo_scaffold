package com.sanhuo.commom.lock;


/**
 * 抢不到分布式锁的重试策略
 *
 * @author sanhuo
 * @date 2020/1/17 10:12
 */
public enum DistributeLockStrategy {
    /**
     * 抢不到锁就不执行方法
     */
    GIVE_UP,
    /**
     * 抢不到锁就尝试重新获取
     */
    RETRY;

}
