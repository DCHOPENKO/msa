package com.iproddy.deliveryservice.kafka.producer;

import com.iproddy.common.util.constant.AsyncConstants;
import com.iproddy.deliveryservice.config.properties.KafkaTopicProperties;
import com.iproddy.deliveryservice.kafka.producer.dto.DeliveryCreateEvent;
import com.iproddy.deliveryservice.mapper.DeliveryCreateEventMapper;
import com.iproddy.deliveryservice.model.entity.Delivery;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DeliveryCreateEventProducer {

    private final KafkaTopicProperties kafkaTopicProperties;
    private final KafkaTemplate<String, DeliveryCreateEvent> kafkaTemplate;
    private final DeliveryCreateEventMapper deliveryCreateEventMapper;

    public void send(Delivery delivery) {
        Objects.requireNonNull(delivery, "delivery must not be null");
        DeliveryCreateEvent event = deliveryCreateEventMapper.toEvent(delivery);

        Message<DeliveryCreateEvent> message = MessageBuilder
                .withPayload(event)
                .setHeader(KafkaHeaders.TOPIC, kafkaTopicProperties.getDeliveryServiceCreatedEventTopic())
                .setHeader(KafkaHeaders.KEY, String.valueOf(event.id()))
                .setHeader(AsyncConstants.IDEMPOTENT_KEY_HEADER_NAME, UUID.randomUUID().toString())
                .build();

        kafkaTemplate.send(message);
    }

}
