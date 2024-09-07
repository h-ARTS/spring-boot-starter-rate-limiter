package com.hanankhan.ratelimiter.services;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class InMemoryRateLimiterService implements IRateLimiterService {

    private final Map<String, Bucket> cache = new ConcurrentHashMap<>();

    @Value("${ratelimiter.inmemory.capacity}")
    public Integer capacity = 10;

    @Override
    public boolean isRateLimited(String key) {
        Bucket bucket = cache.getOrDefault(key, createNewBucket());
        return !bucket.tryConsume(1);
    }

    @Override
    public void hit(String key) {
        cache.computeIfAbsent(key, k -> createNewBucket()).tryConsume(1);
    }

    private Bucket createNewBucket() {
        Bandwidth limit = Bandwidth.classic(capacity, Refill.greedy(capacity, Duration.ofMinutes(1)));
        return Bucket.builder().addLimit(limit).build();
    }
}
