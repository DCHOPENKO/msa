package com.iproddy.deliveryservice.controller.dto;

import com.iproddy.deliveryservice.model.enums.DeliveryStatus;
import io.swagger.v3.oas.annotations.media.Schema;

public enum DeliveryDto {
    ;

    public enum Request {
        ;
        @Schema(name = "DeliveryBaseRequest", description = "Request payload for creating or updating delivery")
        public record Base(
                @Schema(description = "Order identifier associated with this delivery", example = "1001", requiredMode = Schema.RequiredMode.REQUIRED)
                Long orderId,
                @Schema(description = "Customer information", requiredMode = Schema.RequiredMode.REQUIRED)
                CustomerInfoDto.Request.Base customerInfo,
                @Schema(description = "Shipping address", requiredMode = Schema.RequiredMode.REQUIRED)
                ShippingAddressDto.Request.Base shippingAddress,
                @Schema(description = "Current delivery status", example = "CREATED", requiredMode = Schema.RequiredMode.REQUIRED)
                DeliveryStatus status
        ) {
        }
    }

    public enum Response {
        ;
        @Schema(name = "DeliveryBaseResponse", description = "Delivery Response payload")
        public record Base(
                @Schema(description = "Unique delivery identifier", example = "1")
                Long id,
                @Schema(description = "Order identifier associated with this delivery", example = "1001", requiredMode = Schema.RequiredMode.REQUIRED)
                Long orderId,
                @Schema(description = "Customer information", requiredMode = Schema.RequiredMode.REQUIRED)
                CustomerInfoDto.Response.Base customerInfo,
                @Schema(description = "Shipping address", requiredMode = Schema.RequiredMode.REQUIRED)
                ShippingAddressDto.Response.Base shippingAddress,
                @Schema(description = "Current delivery status", example = "CREATED", requiredMode = Schema.RequiredMode.REQUIRED)
                DeliveryStatus status
        ) {
        }
    }
}