package com.myth.distributedsystem.RateLimiter;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class SlidingWindowRateLimiter implements RateLimiter, Runnable {

  private final long maxVisitPerSecond;

  private static final int DEFAULT_BLOCK = 10;
  private static final int DEFAULT_ALLOWED_VISIT_PER_SECOND = 5;
  private final int block;
  private final AtomicInteger[] countPerBlock;

  private AtomicInteger count;
  private volatile int index;

  public SlidingWindowRateLimiter(int block, long maxVisitPerSecond) {
    this.block = block;
    this.maxVisitPerSecond = maxVisitPerSecond;
    countPerBlock = new AtomicInteger[block];
    for (int i = 0; i < block; i++) {
      countPerBlock[i] = new AtomicInteger();
    }
    count = new AtomicInteger(0);
  }

  public SlidingWindowRateLimiter() {
    this(DEFAULT_BLOCK, DEFAULT_ALLOWED_VISIT_PER_SECOND);
  }

  @Override
  public boolean isOverLimit() {
    return currentQPS() > maxVisitPerSecond;
  }

  @Override
  public int currentQPS() {
    return count.get();
  }

  @Override
  public boolean visit() {
    countPerBlock[index].incrementAndGet();
    count.incrementAndGet();
    return isOverLimit();
  }

  @Override
  public void run() {
    System.out.println(isOverLimit());
    System.out.println(currentQPS());
    System.out.println("index:" + index);
    index = (index + 1) % block;
    int val = countPerBlock[index].getAndSet(0);
    count.addAndGet(-val);
  }

  public static void main(String[] args) {
    SlidingWindowRateLimiter slidingWindowRateLimiter = new SlidingWindowRateLimiter(10, 1000);
    ScheduledExecutorService scheduledExecutorService = Executors
        .newSingleThreadScheduledExecutor();
    scheduledExecutorService
        .scheduleAtFixedRate(slidingWindowRateLimiter, 100, 100, TimeUnit.MILLISECONDS);

    new Thread(() -> {
      while (true) {
        slidingWindowRateLimiter.visit();
        try {
          Thread.sleep(10);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }).start();

    new Thread(() -> {
      while (true) {
        slidingWindowRateLimiter.visit();
        try {
          Thread.sleep(10);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }).start();
  }
}