package com.iproddy.orderservice.mapper;

import com.iproddy.orderservice.controller.dto.OrderItemDto;
import com.iproddy.orderservice.model.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.math.BigDecimal;
import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true),
            @Mapping(target = "order", ignore = true),
            @Mapping(target = "totalAmount", expression = "java(calculateLineTotal(request))")
    })
    OrderItem toEntity(OrderItemDto.Request.Base request);

    List<OrderItem> toEntityList(List<OrderItemDto.Request.Base> requests);

    OrderItemDto.Response.Base toResponse(OrderItem entity);

    List<OrderItemDto.Response.Base> toResponseList(List<OrderItem> entities);

    default BigDecimal calculateLineTotal(OrderItemDto.Request.Base request) {
        if (request.price() == null || request.quantity() == null) {
            return BigDecimal.ZERO;
        }
        return request.price().multiply(BigDecimal.valueOf(request.quantity()));
    }
}