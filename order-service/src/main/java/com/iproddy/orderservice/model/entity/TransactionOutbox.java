package com.iproddy.orderservice.model.entity;

import com.iproddy.common.model.enums.OutboxMessageStatus;
import com.iproddy.common.model.enums.OutboxType;
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
@IdClass(TransactionOutboxMessageId.class)
@EqualsAndHashCode(of = {"id", "topic"}, callSuper = false)
@Table(name = "transaction_outbox")
public class TransactionOutbox extends PersistableEntity<TransactionOutboxMessageId> {

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
    private OutboxType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OutboxMessageStatus status;

    @Override
    @Transient
    public TransactionOutboxMessageId getId() {
        return new TransactionOutboxMessageId(id, topic);
    }


}
