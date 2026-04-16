package com.iproddy.orderservice.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.domain.Persistable;

import java.time.LocalDateTime;

@MappedSuperclass
public abstract class PersistableEntity<T> implements Persistable<T> {

    @Column(name = "created_at", insertable = false, updatable = false)
    @ColumnDefault("now()")
    private LocalDateTime createdAt;

    @Override
    public boolean isNew() {
        return createdAt == null;
    }
}
