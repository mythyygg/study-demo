package myth.concurrency;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchDemo {

  public static void main(String[] args) {

    int N = 5;
    final CountDownLatch latch = new CountDownLatch(2);
    for (int i = 0; i < N; i++) {
      new Thread(() -> {
        try {
          System.out.println("子线程" + Thread.currentThread().getName() + "正在执行");
          Thread.sleep((long) Math.random() * 2000);
          System.out.println("子线程" + Thread.currentThread().getName() + "执行完毕");
          latch.countDown();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }).start();
    }
    try {
      System.out.println("等待" + N + "个子线程执行...");
      latch.await();
      System.out.println(N + "个子线程已经执行完毕");
      System.out.println("继续执行主线程");
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}