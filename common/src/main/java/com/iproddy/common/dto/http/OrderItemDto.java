package com.iproddy.common.dto.http;

import java.math.BigDecimal;

public enum OrderItemDto {
    ;
    public enum Request {
        ;
        public record Base(
                String productName,
                Integer quantity,
                BigDecimal price
        ) {}
    }
    public enum Response{
        ;
        public record Base(
                Long id,
                String productName,
                Integer quantity,
                BigDecimal price,
                BigDecimal totalAmount
        ) {}
    }
}
