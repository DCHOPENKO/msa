package com.iproddy.paymentservice.web.filter;

import com.iproddy.paymentservice.config.properties.RateLimiterProperties;
import io.github.bucket4j.Bucket;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Order(1)
@RequiredArgsConstructor
public class RateLimitFilter implements Filter {

    private final RateLimiterProperties rateLimiterProperties;
    private final ConcurrentHashMap<String, Bucket> BUCKETS = new ConcurrentHashMap<>();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {

        String clientIP = request.getRemoteAddr();
        Bucket bucket = BUCKETS.computeIfAbsent(clientIP, this::createBucket);

        if (bucket.tryConsume(1)) {
            chain.doFilter(request, response);
        } else {
            ((HttpServletResponse) response).setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.getWriter().write("Too Many Requests");
        }
    }

    private Bucket createBucket(String key) {
        return Bucket.builder()
                .addLimit(limit -> limit
                        .capacity(rateLimiterProperties.getPerSecond())
                        .refillIntervally(rateLimiterProperties.getPerSecond(), Duration.ofSeconds(1))
                )
                .addLimit(limit -> limit
                        .capacity(rateLimiterProperties.getPerMinute())
                        .refillIntervally(rateLimiterProperties.getPerMinute(), Duration.ofMinutes(1))
                )
                .addLimit(limit -> limit
                        .capacity(rateLimiterProperties.getPerHour())
                        .refillIntervally(rateLimiterProperties.getPerHour(), Duration.ofHours(1))
                )
                .build();
    }
}