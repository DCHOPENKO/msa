package com.iproddy.paymentservice.model.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Payment lifecycle status")
public enum PaymentStatus {
    CREATED,
    PAID,
    DECLINED,
    CANCELLED
}