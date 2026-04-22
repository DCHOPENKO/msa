package com.iproddy.paymentservice.mapper;

import com.iproddy.common.dto.kafka.OrderCreationStatusMessage;
import com.iproddy.paymentservice.controller.dto.CardInfoDto;
import com.iproddy.paymentservice.model.vo.CardInfo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CardInfoMapper {

    CardInfo toEntity(CardInfoDto.Request.Base request);

    CardInfo toEntity(OrderCreationStatusMessage.CardInfo request);

    CardInfoDto.Response.Base toResponse(CardInfo entity);
}