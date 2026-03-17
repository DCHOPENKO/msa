package com.iproddy.paymentservice.mapper;

import com.iproddy.common.dto.http.CardInfoDto;
import com.iproddy.paymentservice.model.vo.CardInfo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CardInfoMapper {

    CardInfo toEntity(CardInfoDto.Request.Base request);

    CardInfoDto.Response.Base toResponse(CardInfo entity);
}