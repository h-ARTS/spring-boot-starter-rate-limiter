package com.hanankhan.ratelimiter.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryRateLimiterServiceTest {

    public static final String CLIENT_KEY = "client-1";

    private InMemoryRateLimiterService service;

    @BeforeEach
    void setUp() {
        service = new InMemoryRateLimiterService();
    }

    @Test
    void shouldAllowRequestWithinLimit() {
        assertFalse(service.isRateLimited(CLIENT_KEY));
    }

    @Test
    void shouldLimitRequestAfterExceedingLimit() {
        for (int i = 0; i < 10; i++) {
            service.hit(CLIENT_KEY);
        }

        // 11th time
        assertTrue(service.isRateLimited(CLIENT_KEY));
    }

    @Test
    void shouldResetLimitAfterTimeWindow() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            service.hit(CLIENT_KEY);
        }

        assertTrue(service.isRateLimited(CLIENT_KEY));

        Thread.sleep(61_000); // Simulate passing of time window

        assertFalse(service.isRateLimited(CLIENT_KEY));
    }
}