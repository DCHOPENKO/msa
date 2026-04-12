package com.iproddy.orderservice.kafla.producer;

import com.iproddy.orderservice.config.properties.KafkaTopicProperties;
import com.iproddy.orderservice.kafla.producer.dto.OrderPaidEvent;
import com.iproddy.orderservice.mapper.OrderPaidEventMapper;
import com.iproddy.orderservice.model.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class OrderPaidEventProducer {

    private final KafkaTopicProperties kafkaTopicProperties;
    private final KafkaTemplate<String, OrderPaidEvent> kafkaTemplate;
    private final OrderPaidEventMapper orderPaidEventMapper;

    public void send(Order order) {
        Objects.requireNonNull(order, "order must not be null");
        OrderPaidEvent event = orderPaidEventMapper.toEvent(order);
        kafkaTemplate.send(kafkaTopicProperties.getOrderServicePaidEventTopic(), String.valueOf(event.id()), event);
    }
}
