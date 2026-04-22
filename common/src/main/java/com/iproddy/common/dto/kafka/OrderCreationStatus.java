package com.iproddy.common.dto.kafka;

public enum OrderCreationStatus {
    DRAFT,
    PAYMENT_PROCESSING,
    PAYMENT_FAILED,
    PAYMENT_REFUNDED,
    PAYMENT_COMPLETED,
    DELIVERY_PROCESSING,
    DELIVERY_CANCELLED,
    DELIVERY_COMPLETED,
}
