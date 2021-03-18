package myth;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

public class NumAndLetterPrinter {
    private static char c = 'A';
    private static int i = 0;
    static final Object monitor = new Object();
    static Lock lock = new ReentrantLock();
    static final Condition cond = lock.newCondition();

    public static void main(String[] args) {
        // new Thread(() -> printer(1, "numThread"), "numThread").start();
        // new Thread(() -> printer(2, "letterThread"), "letterThread").start();

        new Thread(() -> printer2(1, "numThread"), "numThread").start();
        new Thread(() -> printer2(2, "letterThread"), "letterThread").start();
    }

    private static void printer(int mode, String name) {
        synchronized (monitor) {
            for (int i = 0; i < 26; i++) {
                if (mode == 1) {
                    System.out.print((i + 1));
                    monitor.notifyAll();
                    try {
                        // 让当前线程释放锁资源，进入wait状态
                        monitor.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else if (mode == 2) {
                    // 打印字母A-Z
                    System.out.print((char) ('A' + i));
                    // 唤醒其他在等待的线程
                    monitor.notifyAll();
                    try {
                        // 让当前线程释放锁资源，进入wait状态
                        monitor.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            monitor.notifyAll();
        }
    }

    private static void printer2(int mode, String name) {
        lock.lock();
        try {
            for (int i = 0; i < 26; i++) {
                if (mode == 1) {
                    System.out.print((i + 1));
                } else if (mode == 2) {
                    System.out.print((char) ('A' + i));
                }
                cond.signal();
                cond.await();
            }
            cond.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}