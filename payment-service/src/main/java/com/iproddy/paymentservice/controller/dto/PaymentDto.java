package com.iproddy.paymentservice.controller.dto;

import com.iproddy.paymentservice.model.enums.PaymentMethod;
import com.iproddy.paymentservice.model.enums.PaymentStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public enum PaymentDto {
    ;

    public enum Request {
        ;
        @Schema(name = "PaymentBaseRequest", description = "Request payload for creating or updating a payment")
        public record Base(
                @Schema(description = "Identifier of the related order", example = "1001", requiredMode = Schema.RequiredMode.REQUIRED)
                Long orderId,
                @Schema(description = "Payment amount", example = "15999.99", requiredMode = Schema.RequiredMode.REQUIRED)
                BigDecimal amount,
                @Schema(description = "Payment method", example = "CARD", requiredMode = Schema.RequiredMode.REQUIRED)
                PaymentMethod method,
                @Schema(description = "Current payment status", example = "CREATED", requiredMode = Schema.RequiredMode.REQUIRED)
                PaymentStatus status,
                @Schema(description = "Card information. Usually required for card payments.")
                CardInfoDto.Request.Base cardInfo
        ) {
        }
    }

    public enum Response {
        ;
        @Schema(name = "PaymentBaseResponse", description = "Payment response payload")
        public record Base(
                @Schema(description = "Unique payment identifier", example = "1")
                Long id,
                @Schema(description = "Identifier of the related order", example = "1001", requiredMode = Schema.RequiredMode.REQUIRED)
                Long orderId,
                @Schema(description = "Payment amount", example = "15999.99", requiredMode = Schema.RequiredMode.REQUIRED)
                BigDecimal amount,
                @Schema(description = "Payment method", example = "CARD", requiredMode = Schema.RequiredMode.REQUIRED)
                PaymentMethod method,
                @Schema(description = "Current payment status", example = "CREATED", requiredMode = Schema.RequiredMode.REQUIRED)
                PaymentStatus status,
                @Schema(description = "Card information. Usually required for card payments.")
                CardInfoDto.Response.Base cardInfo
        ) {
        }
    }
}