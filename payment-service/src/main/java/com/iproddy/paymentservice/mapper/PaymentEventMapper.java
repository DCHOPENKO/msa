package com.iproddy.paymentservice.mapper;

import com.iproddy.paymentservice.model.entity.Payment;
import com.iproddy.paymentservice.rabbitmq.producer.dto.PaymentResponseEvent;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        uses = {CardInfoMapper.class})
public interface PaymentEventMapper {

    PaymentResponseEvent toResponse(Payment entity);

}