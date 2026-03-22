package com.iproddy.orderservice.http.client.payment.dto;

import java.math.BigDecimal;

public enum PaymentDto {
    ;

    public enum Request {
        ;
        public record Base(
                Long orderId,
                BigDecimal amount,
                PaymentMethod method,
                PaymentStatus status,
                CardInfoDto.Request.Base cardInfo
        ) {
        }
    }

    public enum Response {
        ;
        public record Base(
                Long id,
                Long orderId,
                BigDecimal amount,
                PaymentMethod method,
                PaymentStatus status,
                CardInfoDto.Response.Base cardInfo
        ) {
        }
    }
}