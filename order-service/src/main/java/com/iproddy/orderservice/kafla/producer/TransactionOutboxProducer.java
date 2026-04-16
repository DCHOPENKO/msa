package com.iproddy.orderservice.kafla.producer;

import com.iproddy.common.exception.TransactionOutboxSendingException;
import com.iproddy.common.util.JsonUtil;
import com.iproddy.orderservice.model.entity.TransactionOutbox;
import com.iproddy.orderservice.model.vo.Payload;
import com.iproddy.orderservice.service.TransactionOutboxService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class TransactionOutboxProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final TransactionOutboxService transactionOutboxService;

    @Transactional
    public void send(TransactionOutbox message) {
        try {
            log.info("Sending message with key: {} to kafka topic: {}", message.getId().getId(), message.getTopic());
            Payload payload = message.getPayload();
            var reqMessage = JsonUtil.readValue(payload.getBody(), payload.getEventType().getClazz());

            kafkaTemplate.send(message.getTopic(), message.getId().getId().toString(), reqMessage)
                    .exceptionally(e -> {
                        throw new TransactionOutboxSendingException("Error on sending message '%s'".formatted(message), e);
                    })
                    .get();

            transactionOutboxService.markAsSent(message);
            log.info("Message with key: {} sent successfully to kafka topic: {}",message.getId().getId(), message.getTopic());
        } catch (Exception e) {
            throw new TransactionOutboxSendingException("Error on sending message '%s'".formatted(message), e);
        }
    }

}
