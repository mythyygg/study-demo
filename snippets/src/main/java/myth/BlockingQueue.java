package myth;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class BlockingQueue {
    private final Object[] items;

    private int takeIndex;

    private int putIndex;

    private AtomicInteger count = new AtomicInteger(0);

    private final ReentrantLock putLock = new ReentrantLock();
    private final Condition notFull = putLock.newCondition();
    private final ReentrantLock takeLock = new ReentrantLock();
    private final Condition notEmpty = takeLock.newCondition();

    public BlockingQueue(int capacity) {
        if (capacity <= 0)
            throw new IllegalArgumentException();
        items = new Object[capacity];
    }

    private void enqueue(Object e) {
        items[putIndex] = e;
        if (++putIndex == items.length)
            putIndex = 0;
    }

    private Object dequeue() {
        // 取出takeIndex指向位置中的元素
        // 并将该位置清空
        Object e = items[takeIndex];
        items[takeIndex] = null;

        // takeIndex向后移一位，如果已到末尾则返回队列开头(位置0)
        if (++takeIndex == items.length)
            takeIndex = 0;

        // 返回之前代码中取出的元素e
        return e;
    }

    public void put(Object e) throws InterruptedException {
        int c = -1;
        putLock.lockInterruptibly();
        try {
            while (count.get() == items.length) {
                // 队列已满时进入休眠
                // 等待队列未满条件得到满足
                notFull.await();
            }

            // 执行入队操作，将对象e实际放入队列中
            enqueue(e);

            // 增加元素总数
            c = count.getAndIncrement();

            // 如果在插入后队列仍然没满，则唤醒其他等待插入的线程
            if (c + 1 < items.length)
                notFull.signal();

        } finally {
            putLock.unlock();
        }

        // 如果插入之前队列为空，才唤醒等待弹出元素的线程
        // 为了防止死锁，不能在释放putLock之前获取takeLock
        if (c == 0)
            signalNotEmpty();
    }

    private void signalNotEmpty() {
        // 为了唤醒等待队列非空条件的线程，需要先获取对应的takeLock
        takeLock.lock();
        try {
            // 唤醒一个等待非空条件的线程
            notEmpty.signal();
        } finally {
            takeLock.unlock();
        }
    }

    public Object take() throws InterruptedException {
        Object e;
        int c = -1;
        takeLock.lockInterruptibly();
        try {
            while (count.get() == 0) {
                // 队列为空时进入休眠
                // 等待队列非空条件得到满足
                notEmpty.await();
            }

            // 执行出队操作，将队列中的第一个元素弹出
            e = dequeue();

            // 减少元素总数
            c = count.getAndDecrement();

            // 如果队列在弹出一个元素后仍然非空，则唤醒其他等待队列非空的线程
            if (c - 1 > 0)
                notEmpty.signal();
        } finally {
            takeLock.unlock();
        }

        // 只有在弹出之前队列已满的情况下才唤醒等待插入元素的线程
        // 为了防止死锁，不能在释放takeLock之前获取putLock
        if (c == items.length)
            signalNotFull();

        return e;
    }

    private void signalNotFull() {
        putLock.lock();
        try {
            // 唤醒一个等待队列未满条件的线程
            notFull.signal();
        } finally {
            putLock.unlock();
        }
    }

}