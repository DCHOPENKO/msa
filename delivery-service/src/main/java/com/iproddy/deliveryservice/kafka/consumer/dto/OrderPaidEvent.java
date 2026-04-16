package com.iproddy.deliveryservice.kafka.consumer.dto;

public record OrderPaidEvent(
        Long id,
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
}
