package myth.concurrency;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @description:
 * @author: yuang gang
 * @create: 2019-03-28 11:35
 **/
public class CyclicBarrierDemo {

  public static void main(String[] args) {
    int N = 5;
    CyclicBarrier barrier = new CyclicBarrier(N, () ->
        System.out.println("当前线程" + Thread.currentThread().getName()));

    for (int i = 0; i < N; i++) {
      new Writer(barrier).start();
      new Writer(barrier).start();
      //new Writer1(barrier).start();
    }
  }

  static class Writer extends Thread {

    private CyclicBarrier barrier;

    public Writer(CyclicBarrier barrier) {
      this.barrier = barrier;
    }

    @Override
    public void run() {
      System.out.println("线程" + Thread.currentThread().getName() + "正在写入数据");
      try {
        Thread.sleep(1000);
        System.out.println("线程" + Thread.currentThread().getName() + "写入数据完毕");
        barrier.await();
      } catch (InterruptedException | BrokenBarrierException e) {
        e.printStackTrace();
      }
      System.out.println("所有线程写入完毕，继续处理其他任务...");
    }
  }

  static class Writer1 extends Thread {

    private CyclicBarrier barrier;

    public Writer1(CyclicBarrier barrier) {
      this.barrier = barrier;
    }

    @Override
    public void run() {
      System.out.println("线程*" + Thread.currentThread().getName() + "正在写入数据");
      try {
        Thread.sleep(1000);
        System.out.println("线程*" + Thread.currentThread().getName() + "写入数据完毕");
        barrier.await(800, TimeUnit.MILLISECONDS);
      } catch (InterruptedException | TimeoutException | BrokenBarrierException e) {
        e.printStackTrace();
      }
      System.out.println("*所有线程写入完毕，继续处理其他任务...");
    }
  }
}
