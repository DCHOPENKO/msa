package com.iproddy.orderservice.kafla.consumer;

import com.iproddy.orderservice.mapper.AsyncMessageMapper;
import com.iproddy.orderservice.model.entity.AsyncMessage;
import com.iproddy.orderservice.service.AsyncMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.dao.DataIntegrityViolationException;


import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
public abstract class IdempotentKafkaConsumer<T> {

    private final AsyncMessageService asyncMessageService;
    private final AsyncMessageMapper asyncMessageMapper;

    public void consume(ConsumerRecord<String, T> consumerRecord) {
        log.info("Received message: {} from topic {} partition {}, offset {}", consumerRecord.value(), consumerRecord.topic(), consumerRecord.partition(), consumerRecord.offset());

        UUID idempotentKey;
        try {
            idempotentKey = UUID.fromString(consumerRecord.key());
        } catch (IllegalArgumentException e) {
            log.warn("Message with the wrong idempotent key: " + consumerRecord.key());
            return;
        }

        AsyncMessage message = asyncMessageMapper.toEntity(idempotentKey, consumerRecord.topic(), consumerRecord.value());

        try {
            asyncMessageService.save(message);
        } catch (DataIntegrityViolationException ex) {
            log.warn("Message with the same idempotent key is present in DB: " + idempotentKey);
            return;
        }

        processConsumedMessage(consumerRecord.value());
        log.info("Consumed  message: {} from topic {} partition {}, offset {}", consumerRecord.value(), consumerRecord.topic(), consumerRecord.partition(), consumerRecord.offset());
    }


    public abstract void processConsumedMessage(T message);
}