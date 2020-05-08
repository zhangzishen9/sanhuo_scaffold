package com.sanhuo.commom.lock.signle;


import com.sanhuo.commom.lock.Lock;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 基于链表的可扩展、高性能、公平的自旋锁(乐观锁)
 *
 * @author sanhuo
 * @date 2020/1/20 13:58
 */
public class CLHLock implements Lock {
    /**
     * 锁等待队列的尾部,多个线程之间共享的
     */
    private AtomicReference<QueueNode> tailNode;
    /**
     * 前一个结点,当前线程私有的
     */
    private ThreadLocal<QueueNode> preNode;
    /**
     * 当前结点,当前线程私有的
     */
    private ThreadLocal<QueueNode> currentNode;

    public CLHLock() {
        tailNode = new AtomicReference<>(null);
        currentNode = ThreadLocal.withInitial(QueueNode::new);
        preNode = ThreadLocal.withInitial(() -> null);
    }

    @Override
    public void lock() {
        QueueNode queueNode = currentNode.get();
        //设置自己的状态为locked=true表示需要获取锁
        queueNode.locked = true;
        //链表的尾部设置为本线程的qNode，并将之前的尾部设置为当前线程的preNode
        QueueNode preNode = tailNode.getAndSet(queueNode);
        this.preNode.set(preNode);
        if (preNode != null) {
            //等待上一个线程释放锁,即为false才会停止自旋
            while (preNode.locked) {
            }
        }
    }

    @Override
    public void unlock() {
        QueueNode queueNode = currentNode.get();
        //释放锁操作时将自己的locked设置为false，可以使得自己的后继节点可以结束自旋
        queueNode.locked = false;
        //回收自己这个节点，从虚拟队列中删除
        //将preNode设置为当前的node，那么下一个节点的preNode就变为了当前节点的preNode，这样就将当前节点移出了队列
        currentNode.set(preNode.get());
    }


    private class QueueNode {
        /**
         * true表示该线程需要获取锁，且不释放锁，为false表示线程释放了锁，且不需要锁
         */
        private volatile boolean locked = false;
    }
}
