package com.iproddy.common.dto.kafka;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record OrderItemEventDto(
        String productName,
        Integer quantity,
        BigDecimal price,
        BigDecimal totalAmount
) {
}
