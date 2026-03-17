package com.iproddy.orderservice.model.entity;

import com.iproddy.common.model.entity.BaseEntity;
import com.iproddy.orderservice.model.enums.OrderStatus;
import com.iproddy.common.model.vo.CustomerInfo;
import com.iproddy.common.model.vo.ShippingAddress;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@Table(name = "orders")
@ToString(exclude = "items")
@EqualsAndHashCode(callSuper = true)
public class Order extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Embedded
    private CustomerInfo customerInfo;

    @Embedded
    private ShippingAddress shippingAddress;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();

    private BigDecimal totalAmount;

    public void clearItems() {
        for (OrderItem item : items) {
            item.setOrder(null);
        }
        this.items.clear();
    }

    public void replaceItems(List<OrderItem> items) {
        List<OrderItem> itemsCopy = items == null ? Collections.emptyList() : new ArrayList<>(items);
        if (itemsCopy.isEmpty()) {
            return;
        }
        clearItems();
        for (OrderItem item : itemsCopy) {
            item.setOrder(this);
            this.items.add(item);
        }
        this.totalAmount = calculateTotalAmount(itemsCopy);

    }

    public BigDecimal calculateTotalAmount(List<OrderItem> items) {
        if (items == null || items.isEmpty()) {
            return BigDecimal.ZERO;
        }
        return items.stream()
                .map(OrderItem::getTotalAmount)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}