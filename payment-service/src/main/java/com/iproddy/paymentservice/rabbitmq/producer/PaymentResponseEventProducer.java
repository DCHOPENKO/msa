package com.iproddy.paymentservice.rabbitmq.producer;

import com.iproddy.paymentservice.config.properties.RabbitMqProperties;
import com.iproddy.paymentservice.rabbitmq.producer.dto.PaymentResponseEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentResponseEventProducer {

    private final RabbitTemplate rabbitTemplate;
    private final RabbitMqProperties rabbitMqProperties;

    public void send(PaymentResponseEvent event) {
        PaymentResponseEvent eventToSend = Objects.requireNonNull(event, "event must not be null");
        log.info("Sending event with payment data for paymentId: {}. orderId: {} to queue: {}",
                eventToSend.id(),
                eventToSend.orderId(),
                rabbitMqProperties.paymentResponseQueue());
        rabbitTemplate.convertAndSend(rabbitMqProperties.paymentResponseQueue(), eventToSend);
    }

}
