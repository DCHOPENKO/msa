package com.iproddy.orderservice.mapper;

import com.iproddy.common.model.enums.OutboxMessageStatus;
import com.iproddy.common.model.enums.OutboxType;
import com.iproddy.common.util.JsonUtil;
import com.iproddy.orderservice.config.properties.KafkaTopicProperties;
import com.iproddy.orderservice.kafla.producer.dto.OrderPaidEvent;
import com.iproddy.orderservice.model.entity.TransactionOutbox;
import com.iproddy.orderservice.model.enums.EventType;
import com.iproddy.orderservice.model.vo.Payload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TransactionOutboxMapper {

    private final KafkaTopicProperties kafkaTopicProperties;

    public TransactionOutbox toEntity(OrderPaidEvent event) {
        TransactionOutbox transactionOutbox = new TransactionOutbox();
        transactionOutbox.setId(UUID.randomUUID());
        transactionOutbox.setTopic(kafkaTopicProperties.getOrderServicePaidEventTopic());
        transactionOutbox.setType(OutboxType.OUTBOX);
        transactionOutbox.setStatus(OutboxMessageStatus.CREATED);
        transactionOutbox.setPayload(toPayload(event));
        return transactionOutbox;
    }

    private Payload toPayload(OrderPaidEvent event) {
        Payload payload = new Payload();
        payload.setEventType(EventType.ORDER_PAID);
        payload.setBody(JsonUtil.stringify(event));
        return payload;
    }
}
