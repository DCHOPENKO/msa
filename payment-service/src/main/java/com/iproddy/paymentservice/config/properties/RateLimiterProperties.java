package com.iproddy.paymentservice.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "rate-limiter.sliding-window")
@Component
@Getter
@Setter
public class RateLimiterProperties {
    private int perSecond;
    private int perMinute;
    private int perHour;
}
