package com.iproddy.orderservice.kafla.consumer.dto;

public record DeliveryCreateEvent(
        Long id,
        Long orderId
) {
}
