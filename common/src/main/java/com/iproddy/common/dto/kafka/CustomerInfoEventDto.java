package com.iproddy.common.dto.kafka;

public record CustomerInfoEventDto(
        String customerName,
        String email,
        String phoneNumber
) {
}
