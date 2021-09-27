package com.myth.distributedsystem.RateLimiter;

public interface RateLimiter {

  boolean isOverLimit();

  int currentQPS();

  boolean visit();
}