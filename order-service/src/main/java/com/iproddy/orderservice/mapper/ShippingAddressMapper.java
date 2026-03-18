package com.iproddy.orderservice.mapper;

import com.iproddy.orderservice.controller.dto.ShippingAddressDto;
import com.iproddy.common.model.vo.ShippingAddress;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ShippingAddressMapper {

    ShippingAddress toEntity(ShippingAddressDto.Request.Base request);

    ShippingAddressDto.Response.Base toResponse(ShippingAddress entity);
}