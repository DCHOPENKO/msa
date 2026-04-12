package com.iproddy.orderservice.rabbitmq.producer.dto;

import com.iproddy.orderservice.model.enums.PaymentMethod;
import com.iproddy.orderservice.model.enums.PaymentStatus;

import java.math.BigDecimal;

public record PaymentCreateRequestEvent(
        Long orderId,
        BigDecimal amount,
        PaymentMethod method,
        PaymentStatus status,
        CardInfo cardInfo
) {

public record CardInfo (
        String cardHolder,
        String cardNumber,
        Integer expirationMonth,
        Integer expirationYear
) {}
}

