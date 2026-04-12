package com.iproddy.orderservice.rabbitmq.consumer.dto;

import com.iproddy.orderservice.model.enums.PaymentMethod;
import com.iproddy.orderservice.model.enums.PaymentStatus;

import java.math.BigDecimal;

public record PaymentResponseEvent(
        Long id,
        Long orderId,
        BigDecimal amount,
        PaymentMethod method,
        PaymentStatus status
) {
}
