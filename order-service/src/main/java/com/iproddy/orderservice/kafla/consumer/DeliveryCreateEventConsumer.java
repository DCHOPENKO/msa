package com.iproddy.orderservice.kafla.consumer;

import com.iproddy.orderservice.kafla.consumer.dto.DeliveryCreateEvent;
import com.iproddy.orderservice.model.entity.Order;
import com.iproddy.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeliveryCreateEventConsumer {

    private final OrderService orderService;

    @KafkaListener(topics = "${kafka.topic.delivery-service-created-event-topic}")
    public void listen(DeliveryCreateEvent event) {
        log.info("Received message: {}", event);
        Objects.requireNonNull(event, "event must not be null");
        Order order = orderService.findById(event.orderId());
        if (Objects.isNull(order)) {
            log.warn("For created delivery with id {}, Order with id: {}} not found", event.id(), event.orderId());
            return;
        }
        orderService.markAsShipping(order, event.id());
    }
}
