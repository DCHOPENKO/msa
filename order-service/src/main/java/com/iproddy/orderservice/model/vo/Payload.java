package com.iproddy.orderservice.model.vo;

import com.iproddy.orderservice.model.enums.EventType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class Payload {

    @Column(name = "event_type", nullable = false)
    private EventType eventType;

    @Column(name = "body", nullable = false)
    private String body;
}
