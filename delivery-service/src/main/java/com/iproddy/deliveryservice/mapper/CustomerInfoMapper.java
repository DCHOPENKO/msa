package com.iproddy.deliveryservice.mapper;

import com.iproddy.common.dto.kafka.OrderCreationStatusMessage;
import com.iproddy.common.model.vo.CustomerInfo;
import com.iproddy.deliveryservice.controller.dto.CustomerInfoDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerInfoMapper {

    CustomerInfo toEntity(CustomerInfoDto.Request.Base request);

    CustomerInfo toEntity(OrderCreationStatusMessage.CustomerInfo request);

    CustomerInfoDto.Response.Base toResponse(CustomerInfo entity);
}