package com.iproddy.orderservice.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public enum OrderItemDto {
    ;
    public enum Request {
        ;
        @Schema(name = "OrderItemBaseRequest", description = "Order item request payload")
        public record Base(
                @Schema(description = "Product name", example = "iPhone 15", requiredMode = Schema.RequiredMode.REQUIRED)
                String productName,
                @Schema(description = "Ordered quantity", example = "1", minimum = "1", requiredMode = Schema.RequiredMode.REQUIRED)
                Integer quantity,
                @Schema(description = "Price for a single product unit", example = "999.99", requiredMode = Schema.RequiredMode.REQUIRED)
                BigDecimal price
        ) {}
    }
    public enum Response{
        ;
        @Schema(name = "OrderItemBaseResponse", description = "Order item response payload")
        public record Base(
                @Schema(description = "Unique order item identifier", example = "1")
                Long id,
                @Schema(description = "Product name", example = "iPhone 15", requiredMode = Schema.RequiredMode.REQUIRED)
                String productName,
                @Schema(description = "Ordered quantity", example = "1", minimum = "1", requiredMode = Schema.RequiredMode.REQUIRED)
                Integer quantity,
                @Schema(description = "Price for a single product unit", example = "999.99", requiredMode = Schema.RequiredMode.REQUIRED)
                BigDecimal price,
                @Schema(description = "Calculated total amount for the position", example = "999.99")
                BigDecimal totalAmount
        ) {}
    }
}
