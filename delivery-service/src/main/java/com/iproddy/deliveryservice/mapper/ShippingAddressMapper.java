package com.iproddy.deliveryservice.mapper;

import com.iproddy.common.model.vo.ShippingAddress;
import com.iproddy.common.dto.http.ShippingAddressDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ShippingAddressMapper {

    ShippingAddress toEntity(ShippingAddressDto.Request.Base request);

    ShippingAddressDto.Response.Base toResponse(ShippingAddress entity);
}