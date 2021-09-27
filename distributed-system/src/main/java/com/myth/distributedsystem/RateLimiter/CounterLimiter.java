package com.myth.distributedsystem.RateLimiter;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @description: 计数器限流
 * @author: yuang gang
 * @create: 2019-04-25 15:49
 **/
public class CounterLimiter {

  private int counter;
  private long timeStamp;
  private int maxPermit;
  private int intervalMillSecond;

  public CounterLimiter(int maxPermit, int intervalSecond) {
    this.maxPermit = maxPermit;
    this.intervalMillSecond = intervalSecond * 1000;
    this.timeStamp = System.currentTimeMillis();
  }

  public synchronized boolean tryAcquire(int permit) {
    while (true) {
      long currentTime = System.currentTimeMillis();
      if (currentTime < timeStamp + intervalMillSecond) {
        if (counter + permit <= maxPermit) {
          counter += permit;
          return true;
        } else {
          return false;
        }
      } else {
        counter = 0;
        timeStamp = System.currentTimeMillis();
      }
    }
  }
}
