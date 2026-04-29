package com.iproddy.orderservice.kafla.consumer;

import com.iproddy.common.dto.kafka.OrderCreationStatusMessage;
import com.iproddy.orderservice.model.entity.Order;
import com.iproddy.orderservice.model.enums.OrderStatus;
import com.iproddy.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class OrderCreationStatusMessageConsumer {

    private final OrderService orderService;

    @KafkaListener(topics = "${kafka.topic.order-creation-status-topic}")
    public void consume(OrderCreationStatusMessage message) {

        OrderStatus status = switch (message.status()) {
            case PAYMENT_PROCESSING -> OrderStatus.PAYMENT_PROCESSING;
            case PAYMENT_COMPLETED -> OrderStatus.PAYMENT_COMPLETED;
            case DELIVERY_PROCESSING -> OrderStatus.SHIPPING;
            case DELIVERY_COMPLETED -> OrderStatus.COMPLETED;
            case PAYMENT_FAILED, PAYMENT_REFUNDED, DELIVERY_CANCELLED -> OrderStatus.CANCELLED;
            default -> null;
        };

        if (status == null) {
            return;
        }
        Order order = orderService.findByIdOrThrow(message.orderId());

        if (OrderStatus.CANCELLED == order.getStatus()) {
            return;
        }
        orderService.setStatus(order, status);
        log.info("Order with id {} was updated. Status changed to {}",order.getId(), status);
    }
}
