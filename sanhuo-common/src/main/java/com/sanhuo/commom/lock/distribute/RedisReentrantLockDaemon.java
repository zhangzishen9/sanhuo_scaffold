package com.sanhuo.commom.lock.distribute;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.Collections;
import java.util.List;

/**
 * redis分布式锁的守护线程,用于对执行过长的方法延长锁的有效期
 *
 * @author sanhuo
 * @date 2020/1/20 15:21
 */
@Slf4j
public class RedisReentrantLockDaemon implements Runnable {

    /**
     * 操作成功的比对
     */
    private static final long REDIS_EXPIRE_SUCCESS = 1;


    /**
     * 延时释放redis锁的lua脚本,先判断锁的拥有者,再进行删除
     */
    private final String REDIS_EXPIRE_LUA
            = " if redis.call('get',KEYS[1]) == ARGV[1] " +
            " then " +
            " return redis.call('expire',KEYS[1],ARGV[2]) " +
            " else " +
            " return 0 end";
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
    private long timeOut;

    private RedisTemplate redisTemplate;

    public RedisReentrantLockDaemon(String lockKey, String lockValue, long timeOut, RedisTemplate redisTemplate) {
        this.lockKey = lockKey;
        this.lockValue = lockValue;
        this.timeOut = timeOut;
        this.redisTemplate = redisTemplate;
        this.signal = true;
    }

    /**
     * 守护线程关闭的标记
     */
    private volatile Boolean signal;

    @Override
    public void run() {
        log.info("守护线程启动成功");
        long waitTime = timeOut * 1000 * 2 / 3;
        while (signal) {
            try {
                Thread.sleep(waitTime);
                if (execExpandTimeScript(lockKey, lockValue, timeOut)) {
                    log.info("锁 [ {} ] 延期成功", lockKey);
                } else {
                    log.error("锁 [ {} ] 延期失败", lockKey);
                    this.stop();
                }

            } catch (InterruptedException e) {
                log.info("锁 [ {} ] 的守护线程被中断",lockKey);
            }
        }

    }

    /**
     * lua脚本进行expandTime
     */
    private Boolean execExpandTimeScript(String lockKey, String lockValue, long timeOut) {
        //初始化
        DefaultRedisScript unLockScript = new DefaultRedisScript();
        unLockScript.setResultType(Long.class);
        unLockScript.setScriptText(REDIS_EXPIRE_LUA);
        //keys:redis的get和delete方法操作的键
        List<String> keys = Collections.singletonList(lockKey);
        return (Long) redisTemplate.execute(unLockScript, keys, lockValue, timeOut) == REDIS_EXPIRE_SUCCESS;
    }

    private void stop() {
        this.signal = Boolean.FALSE;
    }
}