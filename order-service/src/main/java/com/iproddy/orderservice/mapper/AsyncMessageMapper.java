package com.iproddy.orderservice.mapper;

import com.iproddy.orderservice.model.enums.AsyncMessageStatus;
import com.iproddy.orderservice.model.enums.AsyncMessageType;
import com.iproddy.common.util.JsonUtil;
import com.iproddy.orderservice.config.properties.KafkaTopicProperties;
import com.iproddy.orderservice.model.entity.AsyncMessage;
import com.iproddy.orderservice.model.enums.EventType;
import com.iproddy.orderservice.model.vo.Payload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AsyncMessageMapper {

    private final KafkaTopicProperties kafkaTopicProperties;

    public <T> AsyncMessage toEntity(T message) {
        AsyncMessage asyncMessage = new AsyncMessage();
        asyncMessage.setId(UUID.randomUUID());
        asyncMessage.setTopic(kafkaTopicProperties.getOrderServicePaidEventTopic());
        asyncMessage.setType(AsyncMessageType.OUTBOX);
        asyncMessage.setStatus(AsyncMessageStatus.CREATED);
        asyncMessage.setPayload(toPayload(message));
        return asyncMessage;
    }

    public <T> AsyncMessage toEntity(UUID id, String topic, T message) {
        AsyncMessage asyncMessage = new AsyncMessage();
        asyncMessage.setId(id);
        asyncMessage.setTopic(topic);
        asyncMessage.setType(AsyncMessageType.INBOX);
        asyncMessage.setStatus(AsyncMessageStatus.RECEIVED);
        asyncMessage.setPayload(toPayload(message));
        return asyncMessage;
    }

    private <T> Payload toPayload(T message) {
        Payload payload = new Payload();
        payload.setEventType(EventType.valueOf(message.getClass()));
        payload.setBody(JsonUtil.stringify(message));
        return payload;
    }
}
