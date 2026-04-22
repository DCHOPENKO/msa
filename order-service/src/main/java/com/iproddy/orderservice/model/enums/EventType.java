package com.iproddy.orderservice.model.enums;

import com.iproddy.orderservice.kafla.consumer.dto.DeliveryCreateEvent;
import com.iproddy.orderservice.kafla.producer.dto.OrderPaidEvent;
import lombok.Getter;

@Getter
public enum EventType {
    ORDER_PAID(OrderPaidEvent.class),
    DELIVERY_CREATED(DeliveryCreateEvent.class);

    EventType(Class<?> clazz) {
        this.clazz = clazz;
    }

    private final Class<?> clazz;

    public static EventType valueOf(Class<?> clazz) {
        for (EventType eventType : EventType.values()) {
            if (eventType.clazz.equals(clazz)) {
                return eventType;
            }
        }
        throw new IllegalArgumentException("No enum constant for " + clazz.getName());
    }
}
