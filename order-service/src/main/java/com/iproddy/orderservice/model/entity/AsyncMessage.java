package com.iproddy.orderservice.model.entity;

import com.iproddy.orderservice.model.enums.AsyncMessageStatus;
import com.iproddy.orderservice.model.enums.AsyncMessageType;
import com.iproddy.orderservice.model.vo.Payload;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@Entity
@IdClass(AsyncMessageId.class)
@EqualsAndHashCode(of = {"id", "topic"}, callSuper = false)
@Table(name = "async_messages")
public class AsyncMessage extends PersistableEntity<AsyncMessageId> {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Id
    @Column(name = "topic", nullable = false)
    private String topic;

    @Column(name = "headers")
    private String headers;

    @Embedded
    private Payload payload;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private AsyncMessageType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private AsyncMessageStatus status;

    @Override
    @Transient
    public AsyncMessageId getId() {
        return new AsyncMessageId(id, topic);
    }


}
