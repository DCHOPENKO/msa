package com.iproddy.deliveryservice.mapper;

import com.iproddy.common.dto.kafka.OrderCreationStatusMessage;
import com.iproddy.common.model.vo.ShippingAddress;
import com.iproddy.deliveryservice.controller.dto.ShippingAddressDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ShippingAddressMapper {

    ShippingAddress toEntity(ShippingAddressDto.Request.Base request);

    ShippingAddress toEntity(OrderCreationStatusMessage.ShippingAddress request);

    ShippingAddressDto.Response.Base toResponse(ShippingAddress entity);
}