package com.iproddy.paymentservice.mapper;

import com.iproddy.paymentservice.controller.dto.CardInfoDto;
import com.iproddy.paymentservice.model.vo.CardInfo;
import com.iproddy.paymentservice.rabbitmq.consumer.dto.PaymentCreateRequestEvent;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CardInfoMapper {

    CardInfo toEntity(CardInfoDto.Request.Base request);

    CardInfo toEntity(PaymentCreateRequestEvent.CardInfo request);

    CardInfoDto.Response.Base toResponse(CardInfo entity);
}