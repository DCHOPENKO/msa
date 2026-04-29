package com.iproddy.paymentservice.kafka.consumer;

import com.iproddy.common.dto.kafka.PaymentRefundingMessage;
import com.iproddy.paymentservice.service.PaymentFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentRefundingConsumer {

    private final PaymentFacade paymentFacade;

    @KafkaListener(topics = "${kafka.topic.payment-refund-event-topic}")
    public void consume(PaymentRefundingMessage message) throws InterruptedException {
            paymentFacade.proceedRefund(message);
    }
}
