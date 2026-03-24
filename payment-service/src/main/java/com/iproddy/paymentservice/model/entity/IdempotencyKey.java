package com.iproddy.paymentservice.model.entity;

import com.iproddy.paymentservice.model.enums.IdempotencyKeyStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "idempotency_keys")
@EqualsAndHashCode(of = "key")
public class IdempotencyKey {

    @Id
    @Column(name = "key_value")
    private Long key;

    @Enumerated(EnumType.STRING)
    private IdempotencyKeyStatus status;

    @Lob
    private String response;

    private LocalDateTime createdAt;

    private int statusCode;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }
}
