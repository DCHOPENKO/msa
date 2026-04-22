package com.iproddy.orderservice.kafla.consumer;

import com.iproddy.orderservice.kafla.consumer.dto.DeliveryCreateEvent;
import com.iproddy.orderservice.mapper.AsyncMessageMapper;
import com.iproddy.orderservice.model.entity.Order;
import com.iproddy.orderservice.service.AsyncMessageService;
import com.iproddy.orderservice.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
public class DeliveryCreateEventConsumer extends IdempotentKafkaConsumer<DeliveryCreateEvent>  {

    private final OrderService orderService;

    public DeliveryCreateEventConsumer(AsyncMessageService asyncMessageService, AsyncMessageMapper asyncMessageMapper, OrderService orderService) {
        super(asyncMessageService, asyncMessageMapper);
        this.orderService = orderService;
    }

    @Override
    @KafkaListener(topics = "${kafka.topic.delivery-service-created-event-topic}")
    public void consume(ConsumerRecord<String, DeliveryCreateEvent> record) {
        super.consume(record);
    }

    @Override
    public void processConsumedMessage(DeliveryCreateEvent message) {
        Order order = orderService.findById(message.orderId());
        if (Objects.isNull(order)) {
            log.warn("For created delivery with id {}, Order with id: {}} not found", message.id(), message.orderId());
            return;
        }
        orderService.markAsShipping(order, message.id());
    }
}
