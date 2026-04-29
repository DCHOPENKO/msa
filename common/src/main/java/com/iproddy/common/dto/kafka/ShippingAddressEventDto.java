package com.iproddy.common.dto.kafka;

public record ShippingAddressEventDto(
        String city,
        String street,
        String house,
        String apartment
) {
}
