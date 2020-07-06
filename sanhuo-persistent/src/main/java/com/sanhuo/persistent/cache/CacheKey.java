package com.sanhuo.persistent.cache;

import com.sanhuo.commom.algorithm.FNV132HASH;
import com.sanhuo.persistent.session.RowBounds;

import java.io.Serializable;

/**
 * <p>
 * 查找缓存的key
 * </p>
 *
 * @author sanhuo
 * @createTime 2020/6/8:20:38
 */
public class CacheKey implements Serializable {

    private String signature;

    private RowBounds rowBounds;

    private String sql;

    private Object[] params;

    private int hashcode;

    private int count;

    private static final int DEFAULT_MULTIPLYER = 37;

    public CacheKey(String signature, RowBounds rowBounds, String sql, Object[] params) {
        this.signature = signature;
        this.rowBounds = rowBounds;
        this.sql = sql;
        this.params = params;
        this.hashcode = FNV132HASH.getHash(signature) &
                FNV132HASH.getHash(rowBounds.toString()) * FNV132HASH.getHash(sql);
        if (params != null && params.length > 0) {
            for (Object param : params) {
                this.hashcode &= FNV132HASH.getHash(param.toString()) - 1;
                this.count++;
            }
        }
    }


    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof CacheKey)) {
            return false;
        }

        final CacheKey cacheKey = (CacheKey) object;

        //先比hashcode，checksum，count，理论上可以快速比出来
        if (hashcode != cacheKey.hashcode) {
            return false;
        }
        if (count != cacheKey.count) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return hashcode;
    }


}
