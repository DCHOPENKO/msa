package com.iproddy.orderservice.controller.dto;

import com.iproddy.orderservice.model.enums.OrderStatus;

import java.math.BigDecimal;
import java.util.List;

public enum OrderDto {
    ;
    public enum  Request{
        ;
        public record Base(
                CustomerInfoDto.Request.Base customerInfo,
                ShippingAddressDto.Request.Base shippingAddress,
                List<OrderItemDto.Request.Base> items,
                OrderStatus status
        ) {}
    }
    public enum Response{
        ;
        public record Base(
                Long id,
                CustomerInfoDto.Response.Base customerInfo,
                ShippingAddressDto.Response.Base shippingAddress,
                OrderStatus status,
                List<OrderItemDto.Response.Base> items,
                BigDecimal totalAmount
        ) {}
    }
}
