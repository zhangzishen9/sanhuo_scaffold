package com.sanhuo.persistent.cache.impl;

import com.sanhuo.persistent.cache.Cache;
import com.sanhuo.persistent.cache.CacheKey;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 一级缓存
 * </p>
 *
 * @author sanhuo
 * @createTime 2020/6/15:20:50
 */
public class BaseCache implements Cache {

    private Map<CacheKey, Object> cache = new HashMap<>();

    @Override
    public void putObject(CacheKey key, Object value) {
        cache.put(key, value);
    }

    @Override
    public Object getObject(CacheKey key) {
        if (cache.containsKey(key)) {
            return cache.get(key);
        }
        return null;
    }

    @Override
    public Boolean hasKey(CacheKey key) {
        return cache.containsKey(key);
    }

    @Override
    public Object removeObject(CacheKey key) {
        if (cache.containsKey(key)) {
            return cache.remove(key);
        }
        return null;
    }

    @Override
    public void clear() {
        cache.clear();
    }

    @Override
    public int getSize() {
        return cache.keySet().size();
    }
}
