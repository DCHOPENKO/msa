package com.iproddy.orderhistoryservice.model.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItem {

    private String productName;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal totalAmount;
}
