package com.sanhuo.persistent.session;

import com.sanhuo.persistent.cache.CacheKey;
import com.sanhuo.persistent.mapping.MappedStatement;

import java.sql.SQLException;
import java.util.List;

/**
 * Executor
 *
 * @author sanhuo
 * @date 2020/2/23 0023 下午 17:26
 */
public interface Executor {

    //不需要ResultHandler
    ResultHandler NO_RESULT_HANDLER = null;

    /**
     * 更新
     */
    int update(MappedStatement ms, Object parameter) throws SQLException;

    /**
     * 查询，带分页，带缓存，BoundSql
     */
    <E> List<E> query(MappedStatement ms, RowBounds rowBounds, CacheKey cacheKey, BoundSql boundSql, Object... parameter) throws SQLException;

    /**
     * 查询，带分页
     */
    <E> List<E> query(MappedStatement ms, RowBounds rowBounds, Object... parameter) throws SQLException;


    /**
     * 创建CacheKey
     */
    CacheKey createCacheKey(MappedStatement ms, RowBounds rowBounds, BoundSql boundSql, Object... parameter);

    /**
     * 判断是否缓存了
     */
    boolean isCached(CacheKey key);

    /**
     * 清理Sessiony一级缓存
     */
    void clearLocalCache();


//    Transaction getTransaction();

//    /**
//     * 提交和回滚，参数是是否要强制
//     */
//    void commit(boolean required) throws SQLException;
//
//    /**
//     * @param required
//     * @throws SQLException
//     */
//    void rollback(boolean required) throws SQLException;
//
//    /**
//     * 判断事务是否关闭
//     */
//    void close(boolean forceRollback);
//
//    boolean isClosed();


}
