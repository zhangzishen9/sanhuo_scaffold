package com.sanhuo.commom.lock;

/**
 * 锁的父类
 *
 * @author sanhuo
 * @date 2020/1/20 11:25
 */
public interface Lock {
    /**
     * 获取锁
     */
    void lock() throws Exception;

    /**
     * 释放锁
     */
    void unlock();
}
