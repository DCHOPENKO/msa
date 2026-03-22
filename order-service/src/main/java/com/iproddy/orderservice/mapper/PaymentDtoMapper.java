package com.iproddy.orderservice.mapper;

import com.iproddy.orderservice.controller.dto.OrderDto;
import com.iproddy.orderservice.http.client.payment.dto.PaymentDto;
import com.iproddy.orderservice.model.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface PaymentDtoMapper {

    @Mappings({
            @Mapping(target = "orderId", source = "order.id"),
            @Mapping(target = "amount", source = "order.totalAmount"),
            @Mapping(target = "method", source = "request.paymentMethod"),
            @Mapping(target = "status", ignore = true),
    })
    PaymentDto.Request.Base toRequest(Order order, OrderDto.Request.Create request);
}
