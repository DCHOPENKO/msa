package com.iproddy.orderservice.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public enum ShippingAddressDto {
    ;
    public enum  Request{
        ;
        @Schema(name = "ShippingAddressBaseRequest", description = "ShippingAddress base information in order request")
        public record Base(
                @Schema(description = "City", example = "Moscow")
                String city,
                @Schema(description = "Street", example = "Lenina street")
                String street,
                @Schema(description = "House number", example = "10")
                String house,
                @Schema(description = "Apartment number", example = "25")
                String apartment
        ) {}
    }
    public enum Response{
        ;
        @Schema(name = "ShippingAddressBaseResponse", description = "ShippingAddress base information in order response")
        public record Base(
                @Schema(description = "City", example = "Moscow")
                String city,
                @Schema(description = "Street", example = "Lenina street")
                String street,
                @Schema(description = "House number", example = "10")
                String house,
                @Schema(description = "Apartment number", example = "25")
                String apartment
        ) {}
    }
}
