package com.iproddy.orderservice.mapper;

import com.iproddy.common.dto.kafka.DeliveryCreationMessage;
import com.iproddy.orderservice.model.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",
        uses = {
                CustomerInfoMapper.class,
                ShippingAddressMapper.class
        })
public interface DeliveryCreationMapper {

    @Mapping(target = "orderId", source = "order.id")
    DeliveryCreationMessage toMessage(Order order);
}
