package com.iproddy.paymentservice.controller.dto;

import com.iproddy.paymentservice.model.enums.PaymentMethod;
import com.iproddy.paymentservice.model.enums.PaymentStatus;

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