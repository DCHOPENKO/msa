package com.iproddy.paymentservice.kafka.consumer;

import com.iproddy.common.dto.kafka.OrderCreationStatus;
import com.iproddy.common.dto.kafka.OrderCreationStatusMessage;
import com.iproddy.paymentservice.service.PaymentFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderCreationStatusMessageConsumer {

    private final PaymentFacade paymentFacade;

    @KafkaListener(topics = "${kafka.topic.order-creation-status-topic}")
    public void consume(OrderCreationStatusMessage message) throws InterruptedException {


        if (OrderCreationStatus.DRAFT == message.status()) {
            paymentFacade.proceedPayment(message);
        }

        if (OrderCreationStatus.DELIVERY_CANCELLED == message.status()) {
            paymentFacade.proceedRefund(message);
        }
    }
}
