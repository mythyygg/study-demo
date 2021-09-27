package myth;

import cn.hutool.core.convert.Convert;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;

/**
 * @description:
 * @author: yuang gang
 * @create: 2018-12-19 19:10
 **/
public class Hutool {

  static int result = 0;

  public static void main(String[] args) throws Exception {
    int a = 1;
    Integer b = 1;
    Map map = new HashMap<>();
    map.put("a", a);
    map.put("b", b);
    Object ax = map.get("a");
    int xx = Convert.toInt(ax);
    System.out.println(xx);

    int N = 3;
    Thread[] threads = new Thread[N];
    final Semaphore[] syncObjects = new Semaphore[N];
    for (int i = 0; i < N; i++) {
      syncObjects[i] = new Semaphore(1);
      if (i != N - 1) {
        syncObjects[i].acquire();
      }
    }
    for (int i = 0; i < N; i++) {
      final Semaphore lastSemphore = i == 0 ? syncObjects[N - 1] : syncObjects[i - 1];
      final Semaphore curSemphore = syncObjects[i];
      final int index = i;
      threads[i] = new Thread(new Runnable() {

        public void run() {
          try {
            while (true) {
              lastSemphore.acquire();
              System.out.println("thread" + index + ": " + result++);
              if (result > 100) {
                System.exit(0);
              }
              curSemphore.release();
            }
          } catch (Exception e) {
            e.printStackTrace();
          }

        }
      });
      threads[i].start();
    }
  }
}
