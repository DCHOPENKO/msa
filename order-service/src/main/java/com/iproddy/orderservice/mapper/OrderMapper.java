package com.iproddy.orderservice.mapper;

import com.iproddy.orderservice.controller.dto.OrderDto;
import com.iproddy.orderservice.model.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring",
        uses = {
        OrderItemMapper.class,
        CustomerInfoMapper.class,
        ShippingAddressMapper.class
})
public interface OrderMapper {

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true),
            @Mapping(target = "totalAmount", ignore = true)
    })
    Order toEntity(OrderDto.Request.Base request);

    OrderDto.Response.Base toResponse(Order entity);

    List<OrderDto.Response.Base> toResponseList(List<Order> entities);
}