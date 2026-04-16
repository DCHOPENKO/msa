package com.iproddy.deliveryservice.kafka.producer.dto;

public record DeliveryCreateEvent(
        Long id,
        Long orderId
) {
}
