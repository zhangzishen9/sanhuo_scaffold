package com.sanhuo.commom.algorithm;

import java.util.SortedMap;

/**
 * 哈希环(一致性Hash算法)
 *
 * @author yunling
 * @date 2020/1/6 15:26
 */
public abstract class HashLoop {

    private static SortedMap<Integer, Object> sortedMap;

    /**
     * 将节点注册到哈希环
     */
    public void put(String key) {
        int hash = getHash(key);
        sortedMap.put(hash, key);
    }

    /**
     * 去掉哈希环中的某个node,并更新到redis
     */
    public void remove(String key) {
        int hash = getHash(key);
        if (sortedMap.containsKey(hash)) {
            sortedMap.remove(hash);
        }
    }

    /**
     * 使用 FNV1_32_HASH 算法来获取对应的hash值
     *
     * @param key
     * @return
     */
    public static int getHash(String key) {
        return FNV132HASH.getHash(key);
    }

    /**
     * 得到应当路由到的结点
     */
    public Object getNode(String key) {
        // 得到带路由的结点的Hash值
        int hash = getHash(key);
        // 得到大于等于该Hash值的所有Map
        SortedMap<Integer, Object> subMap =
                sortedMap.tailMap(hash);
        // 第一个Key就是顺时针过去离node最近的那个结点
        Integer i;
        if (subMap.isEmpty()) {
            //如果没有比该key的hash值大的，则从第一个node开始
            i = sortedMap.firstKey();
        } else {
            //第一个Key就是顺时针过去离node最近的那个结点
            i = subMap.firstKey();
        }
        Object node = sortedMap.get(i);
        return node;

    }

    /**
     * 获取下一个服务器
     */
    public Object getNextNode(String key) {
        // tailMap 获取大于等于的值 ,所以需要+ 1 后才可以获取下一个服务器实例的节点了
        int hash = getHash(key) + 1;
        Integer i;
        if (sortedMap.keySet().size() == 0) {
            throw new RuntimeException("推送服务异常");
        }
        SortedMap<Integer, Object> subMap =
                sortedMap.tailMap(hash);
        if (subMap.isEmpty()) {
            //如果没有比该key的hash值大的，则从第一个node开始
            i = sortedMap.firstKey();
        } else {
            //第一个Key就是顺时针过去离node最近的那个结点
            i = subMap.firstKey();

        }
        Object node = sortedMap.get(i);
        return node;
    }

    /**
     * 判断服务器是否存在哈希环里
     *
     * @param serverUrl
     * @return
     */
    public Boolean isExist(String serverUrl) {
        int hash = getHash(serverUrl);
        return sortedMap.keySet().size() > 0 && sortedMap.containsKey(hash);
    }

}
