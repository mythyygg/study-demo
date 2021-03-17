package myth;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class PrintCondition {
  private final int max = 100000;
  private static int cur = 0;
  private static final ReentrantLock lock = new ReentrantLock();
  private static final Condition cond = lock.newCondition();
  private final Object monitor = new Object();

  public void print1(int mode, String name) {
    while (cur <= max) {
      lock.lock();
      if (cur % 2 == mode && cur <= max) {
        // System.out.println(name + ":" + cur);
        cur++;
      }
      lock.unlock();
    }
  }

  public void print2(int mode, String name) {
    lock.lock();
    try {
      while (cur <= max) {
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
      while (cur < max) {
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
