package com.iproddy.common.dto.kafka;

import lombok.Builder;

@Builder
public record DeliveryCreationMessage(
        Long orderId,
        ShippingAddressEventDto shippingAddress,
        CustomerInfoEventDto customerInfo
) {
}
