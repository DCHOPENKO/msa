package com.iproddy.paymentservice.kafka.consumer;

import com.iproddy.common.dto.kafka.PaymentCreationMessage;
import com.iproddy.paymentservice.service.PaymentFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentCreationConsumer {

    private final PaymentFacade paymentFacade;

    @KafkaListener(topics = "${kafka.topic.payment-create-event-topic}")
    public void consume(PaymentCreationMessage message) throws InterruptedException {
        paymentFacade.proceedPayment(message);
    }
}
