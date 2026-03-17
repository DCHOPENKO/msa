package com.iproddy.deliveryservice.controller.dto;

import com.iproddy.deliveryservice.model.enums.DeliveryStatus;

public enum DeliveryDto {
    ;

    public enum Request {
        ;
        public record Base(
                Long orderId,
                CustomerInfoDto.Request.Base customerInfo,
                ShippingAddressDto.Request.Base shippingAddress,
                DeliveryStatus status
        ) {
        }
    }

    public enum Response {
        ;
        public record Base(
                Long id,
                Long orderId,
                CustomerInfoDto.Response.Base customerInfo,
                ShippingAddressDto.Response.Base shippingAddress,
                DeliveryStatus status
        ) {
        }
    }
}