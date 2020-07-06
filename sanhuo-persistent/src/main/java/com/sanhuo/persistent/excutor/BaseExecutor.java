package com.sanhuo.persistent.excutor;

import com.sanhuo.persistent.cache.CacheKey;
import com.sanhuo.persistent.cache.impl.BaseCache;
import com.sanhuo.persistent.mapping.MappedStatement;
import com.sanhuo.persistent.session.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * <p>
 * 抽象类 提供一级缓存
 * </p>
 *
 * @author sanhuo
 * @createTime 2020/6/8:20:37
 */
public abstract class BaseExecutor implements Executor {

    protected final Configuration configuration;

    /**
     * 一级缓存
     */
    private final BaseCache baseCache;

    protected final Connection connection;

    public BaseExecutor(Configuration configuration, Connection connection) {
        this.configuration = configuration;
        this.baseCache = new BaseCache();
        this.connection = connection;
    }


    @Override
    public <E> List<E> query(MappedStatement ms, RowBounds rowBounds, Object... parameter) throws SQLException {
        //通过输入参数构建boundSql
        BoundSql boundSql = ms.getBoundSql(parameter);
        //构建一级缓存的key
        CacheKey cacheKey = createCacheKey(ms, rowBounds, boundSql, parameter);
        //调用下面重载的方法
        return this.query(ms, rowBounds, cacheKey, boundSql,parameter);
    }


    @Override
    public <E> List<E> query(MappedStatement ms, RowBounds rowBounds, CacheKey cacheKey, BoundSql boundSql, Object... parameter) throws SQLException {
        if (isCached(cacheKey)) {
            //一级缓存有 直接返回
            return (List<E>) baseCache.getObject(cacheKey);
        } else {
            //否 去数据库查询
            return this.queryFromDatabase(ms, rowBounds, cacheKey, boundSql, parameter);
        }
    }

    @Override
    public int update(MappedStatement ms, Object parameter) throws SQLException {
        return 0;
    }

    //从数据库查
    private <E> List<E> queryFromDatabase(MappedStatement ms, RowBounds rowBounds, CacheKey key, BoundSql boundSql, Object... parameter) throws SQLException {
        List<E> list;
        //调用子类的方法区查询
        list = doQuery(ms, rowBounds, boundSql, parameter);
        //加入缓存
        baseCache.putObject(key, list);
        return list;
    }

    /**
     * 模板模式 交给子类实现
     */
    protected abstract <E> List<E> doQuery(MappedStatement ms, RowBounds rowBounds, BoundSql boundSql, Object... params)
            throws SQLException;

    @Override
    public boolean isCached(CacheKey key) {
        return baseCache.hasKey(key);
    }

    @Override
    public CacheKey createCacheKey(MappedStatement ms, RowBounds rowBounds, BoundSql boundSql, Object... parameter) {
        String id = ms.getId();
        String sql = boundSql.getSql();
        return new CacheKey(id, rowBounds, sql, parameter);
    }

    @Override
    public void clearLocalCache() {
        this.baseCache.clear();
    }

}
