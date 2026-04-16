package com.iproddy.deliveryservice.kafka.producer;

import com.iproddy.deliveryservice.config.properties.KafkaTopicProperties;
import com.iproddy.deliveryservice.kafka.producer.dto.DeliveryCreateEvent;
import com.iproddy.deliveryservice.mapper.DeliveryCreateEventMapper;
import com.iproddy.deliveryservice.model.entity.Delivery;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class DeliveryCreateEventProducer {

    private final KafkaTopicProperties kafkaTopicProperties;
    private final KafkaTemplate<String, DeliveryCreateEvent> kafkaTemplate;
    private final DeliveryCreateEventMapper deliveryCreateEventMapper;

    public void send(Delivery delivery) {
        Objects.requireNonNull(delivery, "delivery must not be null");
        DeliveryCreateEvent event = deliveryCreateEventMapper.toEvent(delivery);
        kafkaTemplate.send(kafkaTopicProperties.getDeliveryServiceCreatedEventTopic(), String.valueOf(event.id()), event);
    }

}
