package com.hanankhan.ratelimiter.config;

import com.hanankhan.ratelimiter.interceptor.RateLimitingInterceptor;
import com.hanankhan.ratelimiter.services.IRateLimiterService;
import com.hanankhan.ratelimiter.services.InMemoryRateLimiterService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnMissingBean(IRateLimiterService.class)
@EnableConfigurationProperties(InMemoryRateLimiterService.class)
public class RateLimiterAutoConfiguration {

    @Bean
    @ConditionalOnProperty(value = "ratelimiter.enabled", havingValue = "true", matchIfMissing = true)
    public IRateLimiterService rateLimiterService() {
        return new InMemoryRateLimiterService();
    }

    @Bean
    public RateLimitingInterceptor rateLimitingInterceptor(IRateLimiterService rateLimiterService) {
        return new RateLimitingInterceptor(rateLimiterService);
    }


}
