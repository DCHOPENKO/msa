package com.iproddy.deliveryservice.mapper;

import com.iproddy.deliveryservice.controller.dto.DeliveryDto;
import com.iproddy.deliveryservice.kafka.consumer.dto.OrderPaidEvent;
import com.iproddy.deliveryservice.model.entity.Delivery;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring",
        uses = {
                CustomerInfoMapper.class,
                ShippingAddressMapper.class,
                OrderPaidEventMapper.class
        })
public interface DeliveryMapper {

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true),
    })
    Delivery toEntity(DeliveryDto.Request.Base request);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true),
            @Mapping(target = "orderId", source = "id"),
            @Mapping(target = "shippingAddress", source = "shippingAddress"),
            @Mapping(target = "customerInfo", source = "customerInfo"),
            @Mapping(target = "status", constant = "CREATED")
    })
    Delivery toEntity(OrderPaidEvent event);

    DeliveryDto.Response.Base toResponse(Delivery entity);

    List<DeliveryDto.Response.Base> toResponseList(List<Delivery> entities);
}