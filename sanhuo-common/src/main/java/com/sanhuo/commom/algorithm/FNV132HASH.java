package com.sanhuo.commom.algorithm;

/**
 * <p>
 * FNV1_32_HASH算法
 * </p>
 *
 * @author sanhuo
 * @createTime 2020/6/16:21:28
 */
public class FNV132HASH {

    public static int getHash(String key) {
        final int p = 16777619;
        int hash = (int) 2166136261L;
        for (int i = 0; i < key.length(); i++)
            hash = (hash ^ key.charAt(i)) * p;
        hash += hash << 13;
        hash ^= hash >> 7;
        hash += hash << 3;
        hash ^= hash >> 17;
        hash += hash << 5;

        // 如果算出来的值为负数则取其绝对值
        if (hash < 0)
            hash = Math.abs(hash);
        return hash;
    }
}
