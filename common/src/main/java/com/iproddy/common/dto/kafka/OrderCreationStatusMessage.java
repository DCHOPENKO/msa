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
        CardInfo cardInfo,
        ShippingAddress shippingAddress,
        CustomerInfo customerInfo

) {

    public record ShippingAddress(
            String city,
            String street,
            String house,
            String apartment
    ) {
    }

    public record CustomerInfo(
            String customerName,
            String email,
            String phoneNumber
    ) {
    }

    public record CardInfo(
            String cardHolder,
            String cardNumber,
            Integer expirationMonth,
            Integer expirationYear
    ) {
    }
}
