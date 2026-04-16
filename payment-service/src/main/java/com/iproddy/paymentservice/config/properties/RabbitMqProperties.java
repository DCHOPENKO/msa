package com.iproddy.paymentservice.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "rabbitmq.queue")
public record RabbitMqProperties(

    String paymentCreateRequestQueue,
    String paymentResponseQueue
) {
}
