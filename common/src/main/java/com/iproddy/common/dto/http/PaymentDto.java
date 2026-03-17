package com.iproddy.common.dto.http;


import com.iproddy.common.model.enums.PaymentMethod;
import com.iproddy.common.model.enums.PaymentStatus;

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