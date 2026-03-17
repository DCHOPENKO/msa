package com.iproddy.deliveryservice.mapper;

import com.iproddy.common.model.vo.CustomerInfo;
import com.iproddy.common.dto.http.CustomerInfoDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerInfoMapper {

    CustomerInfo toEntity(CustomerInfoDto.Request.Base request);

    CustomerInfoDto.Response.Base toResponse(CustomerInfo entity);
}