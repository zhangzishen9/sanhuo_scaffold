package com.sanhuo.persistent.excutor;

import com.sanhuo.persistent.cache.CacheKey;
import com.sanhuo.persistent.mapping.MappedStatement;

/**
 * SimpleExecutor
 *
 * @author sanhuo
 * @date 2020/2/23 0023 下午 17:26
 */
public class SimpleExecutor implements Executor {
    @Override
    public void commit(boolean required) {

    }

    @Override
    public void rollback(boolean required) {

    }

    @Override
    public CacheKey createCacheKey(MappedStatement ms) {
        return null;
    }

    @Override
    public boolean isCached(MappedStatement ms, CacheKey key) {
        return false;
    }

    @Override
    public void clearLocalCache() {

    }

    @Override
    public void close(boolean forceRollback) {

    }

    @Override
    public boolean isClosed() {
        return false;
    }
}
