package com.iproddy.orderservice.mapper;

import com.iproddy.common.dto.kafka.CardInfoEventDto;
import com.iproddy.common.dto.kafka.CustomerInfoEventDto;
import com.iproddy.common.dto.kafka.ShippingAddressEventDto;
import com.iproddy.common.model.vo.CustomerInfo;
import com.iproddy.common.model.vo.ShippingAddress;
import com.iproddy.orderservice.controller.dto.OrderDto;
import com.iproddy.common.dto.kafka.OrderCreationStatusMessage;
import com.iproddy.orderservice.model.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface OrderCreationStatusMessageMapper {

    @Mappings({
            @Mapping(target = "orderId", source = "order.id"),
            @Mapping(target = "status", constant = "DRAFT"),
            @Mapping(target = "shippingAddress", source = "order.shippingAddress"),
            @Mapping(target = "customerInfo", source = "order.customerInfo"),
            @Mapping(target = "cardInfo", source = "request.cardInfo")
    })
    OrderCreationStatusMessage toEvent(Order order, OrderDto.Request.Create request);

    ShippingAddressEventDto toEvent(ShippingAddress shippingAddress);

    CustomerInfoEventDto toEvent(CustomerInfo customerInfo);

    CardInfoEventDto toEvent(OrderDto.Request.Create.CardInfo cardInfo);
}
