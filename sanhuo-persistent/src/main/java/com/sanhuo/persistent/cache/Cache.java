package com.sanhuo.persistent.cache;

/**
 * <p>
 * 缓存
 * </p>
 *
 * @author sanhuo
 * @createTime 2020/6/15:20:50
 */
public interface Cache {

    /**
     * 存入值
     *
     * @param key   Can be any object but usually it is a {@link CacheKey}
     * @param value The result of a select.
     */
    void putObject(CacheKey key, Object value);

    /**
     * 获取值
     *
     * @param key The key
     * @return The object stored in the cache.
     */
    Object getObject(CacheKey key);

    /**
     * 是否有该缓存
     *
     * @param key
     * @return
     */
    Boolean hasKey(CacheKey key);

    /**
     * 删除值
     *
     * @param key The key
     * @return The object that was removed
     */
    //
    Object removeObject(CacheKey key);

    /**
     * 清空
     */
    void clear();

    /**
     * 取得大小
     *
     * @return The number of elements stored in the cache (not its capacity).
     */
    int getSize();

}
