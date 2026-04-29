package com.iproddy.paymentservice.mapper;

import com.iproddy.common.dto.kafka.OrderCreationStatus;
import com.iproddy.common.dto.kafka.OrderCreationStatusMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface OrderCreationStatusMessageMapper {

    @Mappings({
            @Mapping(target = "orderId", source = "message.orderId"),
            @Mapping(target = "paymentId", source = "paymentId"),
            @Mapping(target = "customerInfo", source = "message.customerInfo"),
            @Mapping(target = "shippingAddress", source = "message.shippingAddress"),
            @Mapping(target = "cardInfo", source = "message.cardInfo"),
            @Mapping(target = "paymentMethod", source = "message.paymentMethod"),
            @Mapping(target = "totalAmount", source = "message.totalAmount"),
            @Mapping(target = "deliveryId", source = "message.deliveryId"),
            @Mapping(target = "status", source = "status")
    })
    OrderCreationStatusMessage toMessage(
            OrderCreationStatusMessage message,
            Long paymentId,
            OrderCreationStatus status
    );
}
