package myth;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.Semaphore;

public class PrintCondition {
  private final int MAX = 100000;
  private static int cur = 0;
  private static final ReentrantLock lock = new ReentrantLock();
  private static final Condition cond = lock.newCondition();
  private final Object monitor = new Object();

  private Semaphore semEven = new Semaphore(1);
  private Semaphore semOdd = new Semaphore(0);



  void print4(int mode, String name) {
    for (int i = 0; i < MAX; i=i+1) {
      try {
        if ( i % 2 == mode && mode == 0)  {
          semEven.acquire();
          System.out.println(Thread.currentThread().getName() + ":" + i);
          semOdd.release();
        }
        if ( i % 2 == mode && mode == 1)  {
          semOdd.acquire();
          System.out.println(Thread.currentThread().getName() + ":" + i);
          semEven.release();
        }
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }
  }

  void printEven() {
    for (int i = 0; i < MAX; i = i + 2) {
      try {
        semEven.acquire();
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
      System.out.println(Thread.currentThread().getName() + ":" + i);
      semOdd.release();
    }
  }

  void printOdd() {
    for (int i = 1; i < MAX; i = i + 2) {
      try {
        semOdd.acquire();
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
      System.out.println(Thread.currentThread().getName() + ":" + i);
      semEven.release();
    }
  }

  public void print1(int mode, String name) {
    while (cur <= MAX) {
      lock.lock();
      if (cur % 2 == mode && cur <= MAX) {
        // System.out.println(name + ":" + cur);
        cur++;
      }
      lock.unlock();
    }
  }

  public void print2(int mode, String name) {
    lock.lock();
    try {
      while (cur <= MAX) {
        if (cur % 2 == mode) {
          // System.out.println(name + ":" + cur);
          cur++;
        }
        cond.signal();
        cond.await();
      }
      cond.signal();
    } catch (InterruptedException e) {

    } finally {
      lock.unlock();
    }
  }

  public void print3() {
    synchronized (monitor) {
      while (cur < MAX) {
        try {
          System.out.println(cur);
          cur++;
          monitor.notifyAll();
          monitor.wait();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      monitor.notifyAll();
    }
  }

  public static void main(String[] args) {
    PrintCondition p = new PrintCondition();
    new Thread(() -> p.print1(1, "odd")).start();
    new Thread(() -> p.print1(0, "even")).start();
    // new Thread(() -> p.print2(1, "odd")).start();
    // new Thread(() -> p.print2(0, "even")).start();
  }
}
