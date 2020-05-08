package com.sanhuo.commom.lock.signle;

import com.sanhuo.commom.lock.Lock;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 不可重入自旋锁(非公平乐观锁)
 *
 * @author sanhuo
 * @date 2020/1/20 14:22
 */
public class SpinUnReentrantLock implements Lock {
    /**
     * 原子引用,维护当前获取锁的线程
     */
    private AtomicReference<Thread> owner = new AtomicReference<>();
    /**
     * 可重入计数
     */
    private int count = 0;

    @Override
    public void lock() {
        //当前线程
        Thread currentThraed = Thread.currentThread();
        //获取不到锁进行自选
        while (!owner.compareAndSet(null, currentThraed)) {
        }
    }

    @Override
    public void unlock() {
        Thread currentThraed = Thread.currentThread();
        owner.compareAndSet(currentThraed, null);
    }

}
