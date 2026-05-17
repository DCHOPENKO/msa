package com.iproddy.common.dto.kafka;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record OrderCreationStatusMessage(

        Long orderId,
        OrderCreationStatus status,
        Long paymentId,
        Long deliveryId,
        PaymentMethod paymentMethod,
        BigDecimal totalAmount,
        CardInfoEventDto cardInfo,
        ShippingAddressEventDto shippingAddress,
        List<OrderItemEventDto> orderItems,
        CustomerInfoEventDto customerInfo

) {
}
