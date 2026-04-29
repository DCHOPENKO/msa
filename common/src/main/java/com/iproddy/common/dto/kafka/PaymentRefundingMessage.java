package com.iproddy.common.dto.kafka;

import lombok.Builder;

@Builder
public record PaymentRefundingMessage(
        Long orderId
) {
}
