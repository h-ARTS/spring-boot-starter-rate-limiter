package com.hanankhan.ratelimiter.interceptor;

import com.hanankhan.ratelimiter.services.IRateLimiterService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

public class RateLimitingInterceptor implements HandlerInterceptor {

    private final IRateLimiterService rateLimiterService;

    public RateLimitingInterceptor(IRateLimiterService rateLimiterService) {
        this.rateLimiterService = rateLimiterService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String clientId = request.getRemoteAddr();

        if (rateLimiterService.isRateLimited(clientId)) {
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            return false;
        }

        return true;
    }
}
