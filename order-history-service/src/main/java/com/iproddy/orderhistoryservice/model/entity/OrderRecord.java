package com.iproddy.orderhistoryservice.model.entity;

import com.iproddy.orderhistoryservice.model.dto.CardInfo;
import com.iproddy.orderhistoryservice.model.dto.CustomerInfo;
import com.iproddy.orderhistoryservice.model.dto.OrderItem;
import com.iproddy.orderhistoryservice.model.dto.ShippingAddress;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;

@Document(collection = "order-records")
@Data
public class OrderRecord {

    @Id
    private Long id;
    private Long paymentId;
    private Long deliveryId;
    private BigDecimal totalAmount;
    private String paymentMethod;
    private List<OrderItem> orderItems;
    private CardInfo cardInfo;
    private CustomerInfo customerInfo;
    private ShippingAddress shippingAddress;
    private String status;
}
