package com.sanhuo.commom.lock.signle;

import com.sanhuo.commom.lock.Lock;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 可重入自旋锁(非公平乐观锁)
 *
 * @author sanhuo
 * @date 2020/1/20 11:24
 */
public class SpinReentrantLock implements Lock {
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
        if (currentThraed == owner.get()) {
            ++count;
        }
        //获取不到锁进行自选
        while (!owner.compareAndSet(null, currentThraed)) {
        }
    }

    @Override
    public void unlock() {
        Thread currentThraed = Thread.currentThread();
        if (currentThraed == owner.get()) {
            if (count > 0) {
                --count;
            } else {
                //释放锁
                owner.set(null);
            }
        }
    }

}
