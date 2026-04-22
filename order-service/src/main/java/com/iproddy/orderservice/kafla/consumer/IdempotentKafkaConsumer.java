package com.iproddy.orderservice.kafla.consumer;

import com.iproddy.common.util.constant.AsyncConstants;
import com.iproddy.orderservice.mapper.AsyncMessageMapper;
import com.iproddy.orderservice.model.entity.AsyncMessage;
import com.iproddy.orderservice.service.AsyncMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Header;
import org.springframework.dao.DataIntegrityViolationException;


import java.nio.charset.StandardCharsets;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
public abstract class IdempotentKafkaConsumer<T> {

    private final AsyncMessageService asyncMessageService;
    private final AsyncMessageMapper asyncMessageMapper;

    public void consume(ConsumerRecord<String, T> consumerRecord) {
        log.info("Received message: {} from topic {} partition {}, offset {}", consumerRecord.value(), consumerRecord.topic(), consumerRecord.partition(), consumerRecord.offset());

        Header idempotentKeyHeader = consumerRecord.headers().lastHeader(AsyncConstants.IDEMPOTENT_KEY_HEADER_NAME);
        if (idempotentKeyHeader == null) {
            log.error("Idempotent key header is null for consumer record " + consumerRecord);
            return;
        }

        UUID idempotentKey;
        try {
            String rawKey = new String(idempotentKeyHeader.value(), StandardCharsets.UTF_8);
            idempotentKey = UUID.fromString(rawKey);
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