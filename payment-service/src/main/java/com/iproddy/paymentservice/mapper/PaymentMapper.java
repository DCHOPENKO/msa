package com.iproddy.paymentservice.mapper;

import com.iproddy.common.dto.kafka.PaymentCreationMessage;
import com.iproddy.paymentservice.controller.dto.PaymentDto;
import com.iproddy.paymentservice.model.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring",
        uses = {CardInfoMapper.class})
public interface PaymentMapper {

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true),
    })
    Payment toEntity(PaymentDto.Request.Base request);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true),
            @Mapping(target = "status", constant = "CREATED"),
    })
    Payment toEntity(PaymentCreationMessage message);

    PaymentDto.Response.Base toResponse(Payment entity);

    List<PaymentDto.Response.Base> toResponseList(List<Payment> entities);

}