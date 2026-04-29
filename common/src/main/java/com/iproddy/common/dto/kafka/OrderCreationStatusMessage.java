package com.iproddy.common.dto.kafka;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record OrderCreationStatusMessage(

        Long orderId,
        OrderCreationStatus status,
        Long paymentId,
        String deliveryId,
        PaymentMethod paymentMethod,
        BigDecimal totalAmount,
        CardInfoEventDto cardInfo,
        ShippingAddressEventDto shippingAddress,
        CustomerInfoEventDto customerInfo

) {
}
