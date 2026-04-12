package com.iproddy.orderservice.mapper;

import com.iproddy.orderservice.controller.dto.OrderDto;
import com.iproddy.orderservice.model.entity.Order;
import com.iproddy.orderservice.rabbitmq.producer.dto.PaymentCreateRequestEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface PaymentCreateRequestEventMapper {

    @Mappings({
            @Mapping(target = "orderId", source = "order.id"),
            @Mapping(target = "amount", source = "order.totalAmount"),
            @Mapping(target = "method", source = "request.paymentMethod"),
            @Mapping(target = "status", constant = "CREATED"),
            @Mapping(target = "cardInfo", source = "request.cardInfo")
    })
    PaymentCreateRequestEvent toEvent(Order order, OrderDto.Request.Create request);

    PaymentCreateRequestEvent.CardInfo toEvent(OrderDto.Request.Create.CardInfo cardInfo);
}
