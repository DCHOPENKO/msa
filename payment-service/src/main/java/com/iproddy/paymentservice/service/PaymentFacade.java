package com.iproddy.paymentservice.service;

import com.iproddy.common.dto.kafka.OrderCreationStatus;
import com.iproddy.common.dto.kafka.OrderCreationStatusMessage;
import com.iproddy.common.dto.kafka.PaymentCreationMessage;
import com.iproddy.common.dto.kafka.PaymentRefundingMessage;
import com.iproddy.paymentservice.kafka.producer.OrderCreationStatusMessageProducer;
import com.iproddy.paymentservice.mapper.PaymentMapper;
import com.iproddy.paymentservice.model.entity.Payment;
import com.iproddy.paymentservice.model.enums.PaymentStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentFacade {

    private final PaymentService paymentService;
    private final PaymentMapper paymentMapper;
    private final OrderCreationStatusMessageProducer orderCreationStatusMessageProducer;
    private final Random random = new Random();

    public void proceedPayment(PaymentCreationMessage message) throws InterruptedException {

        log.info("Create new payment for order with id: {}", message.orderId());
        Payment entity = paymentMapper.toEntity(message);
        Payment saved = paymentService.save(entity);
        log.info("Payment with id {} for order (id: {}) created. Starting payment process",saved.getId(), message.orderId());

        // имитация оплаты
        Thread.sleep(3000);
        boolean isTrue = random.nextBoolean();
        PaymentStatus paymentStatus = isTrue ? PaymentStatus.PAID : PaymentStatus.CANCELLED;
        OrderCreationStatus orderCreationStatus = isTrue ? OrderCreationStatus.PAYMENT_COMPLETED : OrderCreationStatus.PAYMENT_FAILED;

        paymentService.setStatus(saved, paymentStatus);
        sendOrderCreationStatusMessage(message.orderId(), orderCreationStatus);
        log.info("Payment with id {} for order (id: {}) was {}.",saved.getId(), message.orderId(), isTrue ? "passed" : "failed");
    }

    public void proceedRefund(PaymentRefundingMessage message) throws InterruptedException {

        Payment entity = paymentService.findByOrderId(message.orderId());

        // имитация refund
        Thread.sleep(4000);

        paymentService.setStatus(entity, PaymentStatus.REFUNDED);
        sendOrderCreationStatusMessage(message.orderId(), OrderCreationStatus.PAYMENT_REFUNDED);
        log.info("Payment with id {} for order (id: {}) was refunded.",entity.getId(), message.orderId());
    }

    private void sendOrderCreationStatusMessage(long orderId, OrderCreationStatus status) {
        OrderCreationStatusMessage message = OrderCreationStatusMessage.builder()
                .orderId(orderId)
                .status(status)
                .build();
        orderCreationStatusMessageProducer.send(message);
    }

}
