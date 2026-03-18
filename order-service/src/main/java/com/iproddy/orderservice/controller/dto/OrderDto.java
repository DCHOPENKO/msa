package com.iproddy.orderservice.controller.dto;

import com.iproddy.orderservice.http.client.payment.dto.PaymentMethod;
import com.iproddy.orderservice.model.enums.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.List;

public enum OrderDto {
    ;

    public enum Request {
        ;

        @Schema(name = "OrderCreateRequest", description = "Request payload for creating order")
        public record Create(
                @Schema(description = "Customer information", requiredMode = Schema.RequiredMode.REQUIRED)
                CustomerInfoDto.Request.Base customerInfo,
                @Schema(description = "Shipping address", requiredMode = Schema.RequiredMode.REQUIRED)
                ShippingAddressDto.Request.Base shippingAddress,
                @Schema(description = "List of ordered items", requiredMode = Schema.RequiredMode.REQUIRED)
                List<OrderItemDto.Request.Base> items,
                @Schema(description = "Card information. Usually provided when paymentMethod is CARD.")
                CardInfo cardInfo,
                @Schema(description = "Selected payment method. If present, payment-service will be called.", example = "CARD")
                PaymentMethod paymentMethod,
                @Schema(description = "Initial order status", example = "CREATED", requiredMode = Schema.RequiredMode.REQUIRED)
                OrderStatus status
        ) {
            public record CardInfo(
                    @Schema(description = "Card holder full name as printed on the card", example = "IVAN IVANOV", requiredMode = Schema.RequiredMode.REQUIRED)
                    String cardHolder,
                    @Schema(description = "Bank card number. Digits only, without spaces.", example = "4111111111111111", requiredMode = Schema.RequiredMode.REQUIRED)
                    String cardNumber,
                    @Schema(description = "Card expiration month", example = "12", minimum = "1", maximum = "12", requiredMode = Schema.RequiredMode.REQUIRED)
                    Integer expirationMonth,
                    @Schema(description = "Card expiration year", example = "2028", minimum = "2026", maximum = "2100", requiredMode = Schema.RequiredMode.REQUIRED)
                    Integer expirationYear
            ) {
            }
        }

        @Schema(name = "OrderUpdateRequest", description = "Request payload for updating an existing order")
        public record Update(
                @Schema(description = "Customer information", requiredMode = Schema.RequiredMode.REQUIRED)
                CustomerInfoDto.Request.Base customerInfo,
                @Schema(description = "Shipping address", requiredMode = Schema.RequiredMode.REQUIRED)
                ShippingAddressDto.Request.Base shippingAddress,
                @Schema(description = "List of ordered items", requiredMode = Schema.RequiredMode.REQUIRED)
                List<OrderItemDto.Request.Base> items,
                @Schema(description = "Initial order status", example = "CREATED", requiredMode = Schema.RequiredMode.REQUIRED)
                OrderStatus status
        ) {
        }
    }

    public enum Response {
        ;

        public record Base(
                @Schema(description = "Unique order identifier", example = "1")
                Long id,
                @Schema(description = "Related payment identifier", example = "15")
                Long paymentId,
                @Schema(description = "Customer information", requiredMode = Schema.RequiredMode.REQUIRED)
                CustomerInfoDto.Request.Base customerInfo,
                @Schema(description = "Shipping address", requiredMode = Schema.RequiredMode.REQUIRED)
                ShippingAddressDto.Request.Base shippingAddress,
                @Schema(description = "Initial order status", example = "CREATED", requiredMode = Schema.RequiredMode.REQUIRED)
                OrderStatus status,
                @Schema(description = "List of ordered items", requiredMode = Schema.RequiredMode.REQUIRED)
                List<OrderItemDto.Request.Base> items,
                @Schema(description = "Calculated total amount of the order", example = "124999.98")
                BigDecimal totalAmount
        ) {
        }
    }
}
