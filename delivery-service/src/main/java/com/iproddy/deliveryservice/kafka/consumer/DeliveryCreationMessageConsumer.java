package com.iproddy.deliveryservice.kafka.consumer;

import com.iproddy.common.dto.kafka.DeliveryCreationMessage;
import com.iproddy.deliveryservice.service.DeliveryFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeliveryCreationMessageConsumer {

    private final DeliveryFacade paymentFacade;

    @KafkaListener(topics = "${kafka.topic.delivery-create-event-topic}")
    public void consume(DeliveryCreationMessage message) throws InterruptedException {
            paymentFacade.proceedShipment(message);
    }
}
