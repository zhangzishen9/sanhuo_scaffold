package com.sanhuo.persistent.excutor;

import com.sanhuo.persistent.cache.CacheKey;
import com.sanhuo.persistent.mapping.MappedStatement;

/**
 * Executor
 *
 * @author sanhuo
 * @date 2020/2/23 0023 下午 17:26
 */
public interface Executor {

    /**
     * 提交事务
     */
    void commit(boolean required);

    /**
     * 回滚事务
     */
    void rollback(boolean required);


    /**
     * 创建 CacheKey 对象
     */
    CacheKey createCacheKey(MappedStatement ms);

    /**
     * 判断是否缓存
     */
    boolean isCached(MappedStatement ms, CacheKey key);

    /**
     * 清除本地缓存
     */
    void clearLocalCache();


    // 获得事务
//    Transaction
//
//    getTransaction();


    // 关闭事务
    void close(boolean forceRollback);
    // 判断事务是否关闭
    boolean isClosed();

}
