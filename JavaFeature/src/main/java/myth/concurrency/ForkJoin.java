package myth.concurrency;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: yuang gang
 * @create: 2019-11-10
 **/
public class ForkJoin {

  RaskDemo demo1 = new RaskDemo(0, 10000);

  public static void main(String[] args) throws InterruptedException {
    ForkJoinPool fjp = new ForkJoinPool(2);
    fjp.submit(new RaskDemo(0, 10000));
    fjp.awaitTermination(2, TimeUnit.SECONDS);

    int arr[] = new int[1000];
    Random random = new Random();
    int total = 0;
    for (int i = 0; i < arr.length; i++) {
      int temp = random.nextInt(100);
      total += (arr[i] = temp);
    }
    System.out.println("初始化时的总和=" + total);

    ForkJoinPool forkJoinPool = new ForkJoinPool();

    // 提交可分解的PrintTask任务
//        Future<Integer> future = forkJoinPool.submit(new RecursiveTaskDemo(arr, 0, arr.length));
//        System.out.println("计算出来的总和="+future.get());

    Integer integer = forkJoinPool.invoke(new RecursiveTaskDemo(arr, 0, arr.length));
    System.out.println("计算出来的总和=" + integer);

    // 关闭线程池
    forkJoinPool.shutdown();

  }
}

class RaskDemo extends RecursiveAction {

  /**
   * 每个"小任务"最多只打印20个数
   */
  private static final int MAX = 20;

  private int start;
  private int end;

  public RaskDemo(int start, int end) {
    this.start = start;
    this.end = end;
  }

  @Override
  protected void compute() {
    //当end-start的值小于MAX时，开始打印
    if ((end - start) < MAX) {
      for (int i = start; i < end; i++) {
        System.out.println(Thread.currentThread().getName() + "i的值" + i);
      }
    } else {
      // 将大任务分解成两个小任务
      int middle = (start + end) / 2;
      RaskDemo left = new RaskDemo(start, middle);
      RaskDemo right = new RaskDemo(middle, end);
      left.fork();
      right.fork();
    }
  }
}

class RecursiveTaskDemo extends RecursiveTask<Integer> {

  /**
   * 每个"小任务"最多只打印70个数
   */
  private static final int MAX = 70;
  private int arr[];
  private int start;
  private int end;


  public RecursiveTaskDemo(int[] arr, int start, int end) {
    this.arr = arr;
    this.start = start;
    this.end = end;
  }

  @Override
  protected Integer compute() {
    int sum = 0;
    // 当end-start的值小于MAX时候，开始打印
    if ((end - start) < MAX) {
      for (int i = start; i < end; i++) {
        sum += arr[i];
      }
      return sum;
    } else {
      System.err.println("=====任务分解======");
      // 将大任务分解成两个小任务
      int middle = (start + end) / 2;
      RecursiveTaskDemo left = new RecursiveTaskDemo(arr, start, middle);
      RecursiveTaskDemo right = new RecursiveTaskDemo(arr, middle, end);
      // 并行执行两个小任务
      left.fork();
      right.fork();
      // 把两个小任务累加的结果合并起来
      return left.join() + right.join();
    }
  }

}