package myth.concurrency;

import org.springframework.util.StopWatch;

import java.util.concurrent.*;

/**
 * @description:
 * @author: yuang gang
 * @create: 2019-03-28 12:00
 **/
public class SemaPhoreDemo {

  public static void main(String[] args) {

    int N = 5;
    ExecutorService executorService = Executors.newFixedThreadPool(2);
    CompletionService completionService = new ExecutorCompletionService(executorService);
    StopWatch stopWatch = new StopWatch();
    stopWatch.start("+++");
    for (int i = 0; i < N; i++) {
      final int no = i;
      Runnable run = () -> {
        try {
          System.out.println("Accessing:" + no);
          Thread.sleep(10000);
          System.out.println("======");
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      };
      executorService.execute(run);
    }
    executorService.shutdownNow();
    stopWatch.stop();
    System.out.println(stopWatch.getTotalTimeSeconds());
  }

}
