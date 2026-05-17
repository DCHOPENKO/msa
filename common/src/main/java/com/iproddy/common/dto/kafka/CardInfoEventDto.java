package com.iproddy.common.dto.kafka;

public record CardInfoEventDto(
        String cardHolder,
        String cardNumber,
        Integer expirationMonth,
        Integer expirationYear
) {
}
