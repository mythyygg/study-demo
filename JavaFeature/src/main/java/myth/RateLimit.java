package myth;

import com.google.common.util.concurrent.RateLimiter;

/**
 * @description:
 * @author: yuang gang
 * @create: 2019-08-10
 **/
public class RateLimit {

  private static RateLimiter one = RateLimiter.create(2);//每秒2个
  private static RateLimiter two = RateLimiter.create(2);//每秒2个

  private RateLimit() {
  }

  ;


  public static void acquire(RateLimiter r, int num) {
    double time = r.acquire(num);
    System.out.println("wait time=" + time);
  }

  public static void main(String[] args) throws InterruptedException {
    acquire(one, 1);
    acquire(one, 1);
    acquire(one, 1);
    System.out.println("-----");
    acquire(two, 10);
    acquire(two, 1);

  }

}
