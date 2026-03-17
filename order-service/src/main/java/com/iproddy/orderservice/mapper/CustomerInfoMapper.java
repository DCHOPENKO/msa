package com.iproddy.orderservice.mapper;

import com.iproddy.common.dto.http.CustomerInfoDto;
import com.iproddy.common.model.vo.CustomerInfo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerInfoMapper {

    CustomerInfo toEntity(CustomerInfoDto.Request.Base request);

    CustomerInfoDto.Response.Base toResponse(CustomerInfo entity);
}