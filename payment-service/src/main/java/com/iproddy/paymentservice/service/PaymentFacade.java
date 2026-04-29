package com.iproddy.paymentservice.service;

import com.iproddy.common.dto.kafka.OrderCreationStatus;
import com.iproddy.common.dto.kafka.OrderCreationStatusMessage;
import com.iproddy.paymentservice.kafka.producer.OrderCreationStatusMessageProducer;
import com.iproddy.paymentservice.mapper.OrderCreationStatusMessageMapper;
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
    private final OrderCreationStatusMessageMapper orderCreationStatusMessageMapper;
    private final Random random = new Random();

    public void proceedPayment(OrderCreationStatusMessage message) throws InterruptedException {

        log.info("Create new payment for order with id: {}", message.orderId());
        Payment entity = paymentMapper.toEntity(message);
        Payment saved = paymentService.save(entity);
        orderCreationStatusMessageProducer.send(orderCreationStatusMessageMapper.toMessage(message, saved.getId(), OrderCreationStatus.PAYMENT_PROCESSING));
        log.info("Payment with id {} for order (id: {}) created. Starting payment process",saved.getId(), message.orderId());

        // имитация оплаты
        Thread.sleep(3000);
        boolean isTrue = random.nextBoolean();
        PaymentStatus paymentStatus = isTrue ? PaymentStatus.PAID : PaymentStatus.CANCELLED;
        OrderCreationStatus orderCreationStatus = isTrue ? OrderCreationStatus.PAYMENT_COMPLETED : OrderCreationStatus.PAYMENT_FAILED;

        paymentService.setStatus(saved, paymentStatus);
        orderCreationStatusMessageProducer.send(orderCreationStatusMessageMapper.toMessage(message, saved.getId(), orderCreationStatus));
        log.info("Payment with id {} for order (id: {}) was {}.",saved.getId(), message.orderId(), isTrue ? "passed" : "failed");
    }

    public void proceedRefund(OrderCreationStatusMessage message) throws InterruptedException {

        Payment entity = paymentService.findByIdOrThrow(message.paymentId());

        // имитация refund
        Thread.sleep(4000);

        paymentService.setStatus(entity, PaymentStatus.REFUNDED);
        orderCreationStatusMessageProducer.send(orderCreationStatusMessageMapper.toMessage(message, entity.getId(), OrderCreationStatus.PAYMENT_REFUNDED));
        log.info("Payment with id {} for order (id: {}) was refunded.",entity.getId(), message.orderId());
    }

}
