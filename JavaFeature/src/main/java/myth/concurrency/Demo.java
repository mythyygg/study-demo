package myth.concurrency;

import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @description:
 * @author: yuang gang
 * @create: 2019-03-29 18:46
 **/
public class Demo {

  private static Lock lock = new ReentrantLock();

  public static void main(String[] args) {
    System.out.println("atatatatatatatat".hashCode());
    System.out.println("c6atatatatatatbU".hashCode());
       /* ReentrantLock lock = new ReentrantLock();
        Object o = new Object();
        ExecutorService service = new ThreadPoolExecutor(1,10,10,TimeUnit.SECONDS, new ArrayBlockingQueue<>(10)){
            public int prestartAllCoreThreads() {
                return 1;
            }
        };

        List<Integer> a = new ArrayList<Integer>() {
            {add(1);add(2);}
        };
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        System.out.println(a.size());*/
    long time = 1000;
    for (int i = 0; i < 10; i++) {
      int finalI = i;
      new Thread(() -> {
        lock.lock();
        try {
          System.out.println("thread-" + finalI + " start");
          for (int j = 0; j < 1000000; j++) {
            new HashMap<Integer, String>(10);
          }
        } finally {
          lock.unlock();
        }
      }).start();
    }
  }
}
