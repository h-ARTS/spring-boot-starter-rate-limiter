package com.hanankhan.ratelimiter.services;

public interface IRateLimiterService {
    boolean isRateLimited(String key);
    void hit(String key);
}
