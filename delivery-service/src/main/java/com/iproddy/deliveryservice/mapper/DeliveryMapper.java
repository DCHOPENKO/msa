package com.iproddy.deliveryservice.mapper;

import com.iproddy.common.dto.http.DeliveryDto;
import com.iproddy.deliveryservice.model.entity.Delivery;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring",
        uses = {
        CustomerInfoMapper.class,
        ShippingAddressMapper.class
})
public interface DeliveryMapper {

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true),
    })
    Delivery toEntity(DeliveryDto.Request.Base request);

    DeliveryDto.Response.Base toResponse(Delivery entity);

    List<DeliveryDto.Response.Base> toResponseList(List<Delivery> entities);
}