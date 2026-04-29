package com.iproddy.orderservice.kafla.producer;

import com.iproddy.common.exception.TransactionOutboxSendingException;
import com.iproddy.orderservice.config.properties.KafkaTopicProperties;
import com.iproddy.common.dto.kafka.OrderCreationStatusMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderCreationStatusMessageProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final KafkaTopicProperties  kafkaTopicProperties;

    @Transactional
    public void send(OrderCreationStatusMessage message) {
            kafkaTemplate.send(kafkaTopicProperties.getOrderCreationStatusTopic(), String.valueOf(message.orderId()), message)
                    .exceptionally(e -> {
                        throw new TransactionOutboxSendingException("Error on sending message '%s'".formatted(message), e);
                    });
    }

}
