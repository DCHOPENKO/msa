package com.iproddy.paymentservice.model.entity;

import com.iproddy.common.model.entity.BaseEntity;
import com.iproddy.common.model.enums.PaymentMethod;
import com.iproddy.common.model.enums.PaymentStatus;
import com.iproddy.paymentservice.model.vo.CardInfo;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "payments")
@EqualsAndHashCode(callSuper = true)
public class Payment extends BaseEntity {

    private Long orderId;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @Enumerated(EnumType.STRING)
    private PaymentMethod method;

    @Embedded
    private CardInfo cardInfo;
}