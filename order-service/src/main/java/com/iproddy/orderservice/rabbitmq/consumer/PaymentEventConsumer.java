package com.iproddy.orderservice.rabbitmq.consumer;

import com.iproddy.orderservice.model.entity.Order;
import com.iproddy.orderservice.rabbitmq.consumer.dto.PaymentResponseEvent;
import com.iproddy.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentEventConsumer {

    private final OrderService orderService;

    @RabbitListener(queues = "${rabbitmq.queue.payment-response_queue}")
    public void handle(PaymentResponseEvent message) {
        log.info("Received message: {}", message);
        Optional<Order> optOrder = orderService.findById(message.orderId());
        optOrder.ifPresentOrElse(
                it -> orderService.setPaymentId(it, message.id()), () -> log.warn("For created payment with id {}, Order with id: {}} not found", message.id(), message.orderId())
        );
    }
}
