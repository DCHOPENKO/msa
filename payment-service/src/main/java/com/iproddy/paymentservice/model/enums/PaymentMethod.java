package com.iproddy.paymentservice.model.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Available payment methods")
public enum PaymentMethod {
    CARD,
    CASH
}