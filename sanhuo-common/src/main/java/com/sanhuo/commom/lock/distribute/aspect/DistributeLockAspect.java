package com.sanhuo.commom.lock.distribute.aspect;

import com.sanhuo.commom.lock.DistributeLockStrategy;
import com.sanhuo.commom.lock.distribute.RedisReentrantLock;
import com.sanhuo.commom.lock.distribute.RedisReentrantLockDaemon;
import com.sanhuo.commom.lock.distribute.RedisReentrantLockUtils;
import com.sanhuo.commom.lock.distribute.annotation.DistributeLock;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 分布式锁注解的切面
 *
 * @author sanhuo
 * @date 2020/1/16 17:32
 */
@Aspect
@Slf4j
//@Component
//todo 优化形式
public class DistributeLockAspect {
    /**
     * 需要实现这个方法获取redisTemplate
     *
     * @return
     */


    /**
     * 初次重试时间 1s
     */
    private static final long RETRY_TIME = 1000;

    /**
     * 重试时间倍数
     */
    private static final long RETRY_TIME_MULTIPLE = 2;

    /**
     * 分布式锁注解的切点
     */
    @Pointcut("@annotation(com.sanhuo.commom.lock.distribute.annotation.DistributeLock)"
            + "|| @within(com.sanhuo.commom.lock.distribute.annotation.DistributeLock)")
    public void distributeLockPointCut() {

    }

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 环绕处理
     */
    @Around("distributeLockPointCut()")
    public Object distributeLockAroundAop(ProceedingJoinPoint joinPoint) throws Throwable {
        //获取注解
        DistributeLock distributedLock = RedisReentrantLockUtils.getDistributedLock(joinPoint);
        //分布式锁key
        String lockKey = RedisReentrantLockUtils.getLockKey(joinPoint);
        log.info("当前方法LockKey:{}", lockKey);
        //分布式锁value
        String lockValue = RedisReentrantLockUtils.getLockValue();
        log.info("当前线程LockValue:{}", lockValue);
        //分布式锁超时时间,防止代码崩了之后锁一直在卡死后面的获取锁的线程
        long lockKeyTimeOut = distributedLock.timeOut();
        //获取不到锁之后的策略
        DistributeLockStrategy strategy = distributedLock.strategy();
        //初始化锁
        RedisReentrantLock lock = new RedisReentrantLock(lockKey, lockValue, lockKeyTimeOut, strategy, redisTemplate);
        //初始化守护线程
        RedisReentrantLockDaemon daemon = new RedisReentrantLockDaemon(lockKey, lockValue, lockKeyTimeOut, redisTemplate);
        Thread damonThread = new Thread(daemon);
        try {
            //获取锁,成功获取到锁才会运行下去
            lock.lock();
            //获取锁成功后启动 续期的守护线程
            damonThread.setDaemon(Boolean.TRUE);
            damonThread.start();
            //执行方法
            return joinPoint.proceed();
        } finally {
            //中断线程
            damonThread.interrupt();
            //释放锁
            lock.unlock();
        }
    }


}
