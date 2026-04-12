package com.iproddy.paymentservice.rabbitmq.consumer;

import com.iproddy.paymentservice.mapper.PaymentEventMapper;
import com.iproddy.paymentservice.mapper.PaymentMapper;
import com.iproddy.paymentservice.model.entity.Payment;
import com.iproddy.paymentservice.rabbitmq.consumer.dto.PaymentCreateRequestEvent;
import com.iproddy.paymentservice.rabbitmq.producer.PaymentResponseEventProducer;
import com.iproddy.paymentservice.rabbitmq.producer.dto.PaymentResponseEvent;
import com.iproddy.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentCreateEventConsumer {

    private final PaymentService paymentService;
    private final PaymentMapper paymentMapper;
    private final PaymentResponseEventProducer paymentResponseEventProducer;
    protected final PaymentEventMapper paymentEventMapper;

    @RabbitListener(queues = "${rabbitmq.queue.payment-create-request_queue}")
    public void handle(PaymentCreateRequestEvent message) {
        log.info("Received message: {}", message);
        Payment entity = paymentService.save(paymentMapper.toEntity(message));
        log.info("Payment with id: {}, created", entity.getId());
        PaymentResponseEvent event = paymentEventMapper.toResponse(entity);
        paymentResponseEventProducer.send(event);
    }
}
