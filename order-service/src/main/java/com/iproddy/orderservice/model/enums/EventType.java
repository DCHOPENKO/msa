package com.iproddy.orderservice.model.enums;

import com.iproddy.orderservice.kafla.producer.dto.OrderPaidEvent;
import lombok.Getter;

@Getter
public enum EventType {
    ORDER_PAID(OrderPaidEvent.class);

    EventType(Class<?> clazz) {
        this.clazz = clazz;
    }

    private final Class<?> clazz;
}
