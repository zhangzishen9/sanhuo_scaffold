package com.sanhuo.commom.lock.distribute;

import com.alibaba.fastjson.JSONObject;
import com.sanhuo.commom.lock.DistributeLockStrategy;
import com.sanhuo.commom.lock.Lock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 基于redis的分布式可重入(非公平)自旋锁
 * 1.   只有一个客户端 (唯一标识为 mac地址+jvm的id+thread的id) 能获取锁
 * 2.   客户端崩溃后超出规定时间后锁也会自动解除
 * 3.   同一客户端在请求到锁后可重复获取锁
 * 4.   加锁和解锁必须是同一个客户端
 * 5.   非公平锁
 * 6.   如果客户端没有崩溃,守护线程会在锁超时时间的2/3阶段进行锁的续期
 *
 * @author sanhuo
 * @date 2020/1/20 15:09
 */
@Slf4j
public class RedisReentrantLock implements Lock {
    /**
     * 有同一个线程的资源才有重入的概念,所以在本地上进行
     */
    private ThreadLocal<AtomicInteger> lockCount;

    /**
     * 锁键
     */
    private String lockKey;

    /**
     * 锁值
     */
    private String lockValue;

    /**
     * 过期时间
     */
    private long lockTimeOut;

    /**
     * 获取锁的策略
     */
    private DistributeLockStrategy strategy;

    private RedisTemplate redisTemplate;

    public RedisReentrantLock(String lockKey, String lockValue, long lockTimeOut, DistributeLockStrategy strategy, RedisTemplate redisTemplate) {
        this.lockKey = lockKey;
        this.lockValue = lockValue;
        this.lockTimeOut = lockTimeOut;
        this.strategy = strategy;
        this.redisTemplate = redisTemplate;
        //初始化当前线程的重入次数
        lockCount = ThreadLocal.withInitial(AtomicInteger::new);
    }

    /**
     * 自旋间隔 单位毫秒
     */
    private static final long SPIN_TIME = 100;

    /**
     * 释放redis锁成功标志
     */
    private final long REDIS_UNLOCK_SUCCESS = 1;

    /**
     * 释放redis锁的lua脚本,先判断锁的拥有者,再进行删除
     */
    private final String REDIS_UNLOCK_LUA
            = " if redis.call('get',KEYS[1]) == ARGV[1] " +
            " then " +
            " return redis.call('del',KEYS[1]) " +
            " else " +
            " return 0 end";

    @Override
    public void lock() throws Exception {
        Boolean isGetLock = redisTemplate.opsForValue().setIfAbsent(lockKey, lockValue, lockTimeOut, TimeUnit.SECONDS);
        //尝试获取重入锁
        if (!isGetLock) {
            //相同拥有者可重入锁
            if (isLockOwner(lockKey)) {
                Integer count = lockCount.get().incrementAndGet();
                log.info("重入锁[ {} ] 成功,当前LockCount: {}", count);
                isGetLock = true;
            }

        }
        switch (strategy) {
            case RETRY:
                //自旋
                while (!isGetLock) {
                    isGetLock = redisTemplate.opsForValue().setIfAbsent(lockKey, lockValue, lockTimeOut, TimeUnit.SECONDS);
                    Thread.sleep(SPIN_TIME);
                }
                log.info("获取锁[ {} ]成功", lockKey);
                break;
            case GIVE_UP:
                //放弃
                if (!isGetLock) {
                    throw new Exception("获取锁 " + lockKey + " 失败 , 退出方法");
                }
        }
    }

    @Override
    public void unlock() {
        Integer count = lockCount.get().get();
        //count == 0 说明当前没有重入锁
        if (count == 0) {
            //通过lua脚本删除redis分布式锁,保证get del 两步操作的原子性
            if (execUnLockScript(lockKey, RedisReentrantLockUtils.getLockValue())) {
                log.info("释放锁 [ {} ] 成功", lockKey);
            } else {
                log.error("释放锁 [ {} ] 失败", lockKey);
            }

        } else {
            //count --
            lockCount.get().decrementAndGet();
            log.info("重入[ {} ] 锁 LockCount -1 ,当前lockCount : {}", lockKey, lockCount.get().get());
        }


    }

    /**
     * lua脚本进行unlock
     */
    private Boolean execUnLockScript(String lockKey, String lockValue) {
        //初始化
        DefaultRedisScript unLockScript = new DefaultRedisScript();
        unLockScript.setResultType(Long.class);
        unLockScript.setScriptText(REDIS_UNLOCK_LUA);
        //keys:redis的get和delete方法操作的键
        List<String> keys = Collections.singletonList(lockKey);
        return (Long) redisTemplate.execute(unLockScript, keys, lockValue) == REDIS_UNLOCK_SUCCESS;
    }


    /**
     * 判断当前线程是否是锁的拥有者
     */
    private Boolean isLockOwner(String lockKey) {
        if (redisTemplate.hasKey(lockKey)) {
            String lockValueJsonString = (String) redisTemplate.opsForValue().get(lockKey);
            JSONObject lockValue = JSONObject.parseObject(lockValueJsonString);
            //当前mac地址
            String currentMac = RedisReentrantLockUtils.getLocalMac();
            //当前jvm地址
            Integer currentJvmId = RedisReentrantLockUtils.getJVMId();
            //当前线程地址
            long currentThreadId = RedisReentrantLockUtils.getThreadId();
            return currentMac.equals(lockValue.get(RedisReentrantLockUtils.MAC))
                    && currentJvmId == Integer.parseInt(lockValue.get(RedisReentrantLockUtils.JVM).toString())
                    && currentThreadId == Integer.parseInt(lockValue.get(RedisReentrantLockUtils.THREAD).toString());
        }
        return false;
    }

}



