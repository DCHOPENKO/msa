package com.iproddy.orderservice.model.entity;

import com.iproddy.common.model.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "order_items")
@EqualsAndHashCode(callSuper = true)
public class OrderItem extends BaseEntity {

    private String productName;

    private Integer quantity;

    private BigDecimal price;

    private BigDecimal totalAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;
}
