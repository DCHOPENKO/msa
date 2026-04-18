package com.iproddy.orderservice.mapper;

import com.iproddy.orderservice.model.enums.AsyncMessageStatus;
import com.iproddy.orderservice.model.enums.AsyncMessageType;
import com.iproddy.common.util.JsonUtil;
import com.iproddy.orderservice.config.properties.KafkaTopicProperties;
import com.iproddy.orderservice.kafla.producer.dto.OrderPaidEvent;
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

    public AsyncMessage toEntity(OrderPaidEvent event) {
        AsyncMessage asyncMessage = new AsyncMessage();
        asyncMessage.setId(UUID.randomUUID());
        asyncMessage.setTopic(kafkaTopicProperties.getOrderServicePaidEventTopic());
        asyncMessage.setType(AsyncMessageType.OUTBOX);
        asyncMessage.setStatus(AsyncMessageStatus.CREATED);
        asyncMessage.setPayload(toPayload(event));
        return asyncMessage;
    }

    private Payload toPayload(OrderPaidEvent event) {
        Payload payload = new Payload();
        payload.setEventType(EventType.ORDER_PAID);
        payload.setBody(JsonUtil.stringify(event));
        return payload;
    }
}
