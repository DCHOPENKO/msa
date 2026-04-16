package com.iproddy.deliveryservice.kafka.consumer;

import com.iproddy.deliveryservice.kafka.consumer.dto.OrderPaidEvent;
import com.iproddy.deliveryservice.mapper.DeliveryMapper;
import com.iproddy.deliveryservice.model.entity.Delivery;
import com.iproddy.deliveryservice.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderPaidEventConsumer {

    private final DeliveryService deliveryService;
    private final DeliveryMapper deliveryMapper;

    @KafkaListener(
            topics = "${kafka.topic.order-service-paid-event-topic}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void listen(OrderPaidEvent event) {
        log.info("Received message: {}", event);
        Objects.requireNonNull(event, "event must not be null");
        Delivery delivery = deliveryService.save(deliveryMapper.toEntity(event));
        log.info("Created delivery with id: {}", delivery.getId());
    }

}
