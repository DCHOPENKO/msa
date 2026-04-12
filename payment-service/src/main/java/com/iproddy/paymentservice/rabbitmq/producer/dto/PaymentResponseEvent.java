package com.iproddy.paymentservice.rabbitmq.producer.dto;


import com.iproddy.paymentservice.model.enums.PaymentMethod;
import com.iproddy.paymentservice.model.enums.PaymentStatus;

import java.math.BigDecimal;

public record PaymentResponseEvent(
        Long id,
        Long orderId,
        BigDecimal amount,
        PaymentMethod method,
        PaymentStatus status
) {
}
