package com.iproddy.common.dto.kafka;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record PaymentCreationMessage(
        Long orderId,
        PaymentMethod paymentMethod,
        BigDecimal totalAmount,
        CardInfoEventDto cardInfo
) {
        }
