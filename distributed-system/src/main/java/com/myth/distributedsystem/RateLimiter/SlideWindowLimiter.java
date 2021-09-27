package com.myth.distributedsystem.RateLimiter;

import java.util.LinkedList;

/**
 * @description:
 * @author: yuang gang
 * @create: 2019-04-25 16:09
 **/
public class SlideWindowLimiter {

  private LinkedList<Integer> deque = new LinkedList<>();
  private int windowSize;
  private int currentWindowPermit;
  private int windowIntervalMillSecond;

  private int maxPermit;
  private long timeStamp;
  private int intervalMillSecond;
  private int counter;

  public SlideWindowLimiter(int maxPermit, int intervalMillSecond, int windowSize) {
    this.timeStamp = System.currentTimeMillis();
    this.maxPermit = maxPermit;
    this.intervalMillSecond = intervalMillSecond * 1000;
    this.windowSize = windowSize;
    this.windowIntervalMillSecond = this.intervalMillSecond / windowSize;
  }

  public synchronized boolean tryAcquire(int permit) {
    while (true) {
      long now = System.currentTimeMillis();
      if (now <= timeStamp + windowIntervalMillSecond) {
        if (counter + currentWindowPermit + permit <= maxPermit) {
          currentWindowPermit += permit;
          return true;
        } else {
          return false;
        }
      } else {
        timeStamp = now;
        deque.offerLast(currentWindowPermit);
        counter += currentWindowPermit;

      }
    }
  }
}
