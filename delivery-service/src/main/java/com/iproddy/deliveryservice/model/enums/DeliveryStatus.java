package com.iproddy.deliveryservice.model.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Delivery lifecycle status")
public enum DeliveryStatus {
    CREATED,
    IN_PROGRESS,
    DELIVERED,
    CANCELLED
}