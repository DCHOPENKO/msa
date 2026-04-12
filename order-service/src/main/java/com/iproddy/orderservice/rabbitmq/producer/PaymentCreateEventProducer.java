package com.iproddy.orderservice.rabbitmq.producer;

import com.iproddy.orderservice.config.properties.RabbitMqProperties;
import com.iproddy.orderservice.rabbitmq.producer.dto.PaymentCreateRequestEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentCreateEventProducer {

    private final RabbitTemplate rabbitTemplate;
    private final RabbitMqProperties rabbitMqProperties;

    public void send(PaymentCreateRequestEvent event) {
        PaymentCreateRequestEvent eventToSend = Objects.requireNonNull(event, "event must not be null");
        log.info("Sending payment create event for orderId: {} to queue: {}",
                eventToSend.orderId(),
                rabbitMqProperties.paymentCreateRequestQueue());
        rabbitTemplate.convertAndSend(rabbitMqProperties.paymentCreateRequestQueue(), eventToSend);
    }

}
