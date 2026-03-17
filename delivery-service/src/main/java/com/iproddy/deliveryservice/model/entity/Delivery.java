package com.iproddy.deliveryservice.model.entity;

import com.iproddy.common.model.entity.BaseEntity;
import com.iproddy.common.model.vo.CustomerInfo;
import com.iproddy.common.model.vo.ShippingAddress;
import com.iproddy.common.model.enums.DeliveryStatus;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "deliveries")
@EqualsAndHashCode(callSuper = true)
public class Delivery extends BaseEntity {

    private Long orderId;

    @Embedded
    private ShippingAddress shippingAddress;

    @Embedded
    private CustomerInfo customerInfo;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;
}