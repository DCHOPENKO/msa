package com.iproddy.deliveryservice.kafka.consumer;

import com.iproddy.common.dto.kafka.OrderCreationStatus;
import com.iproddy.common.dto.kafka.OrderCreationStatusMessage;
import com.iproddy.deliveryservice.service.DeliveryFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderCreationStatusMessageConsumer {

    private final DeliveryFacade paymentFacade;

    @KafkaListener(topics = "${kafka.topic.order-creation-status-topic}")
    public void consume(OrderCreationStatusMessage message) throws InterruptedException {
        if (OrderCreationStatus.PAYMENT_COMPLETED == message.status()) {
            paymentFacade.proceedShipment(message);
        }
    }
}
