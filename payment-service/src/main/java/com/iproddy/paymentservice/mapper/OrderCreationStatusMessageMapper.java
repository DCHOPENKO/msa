package com.iproddy.paymentservice.mapper;

import com.iproddy.common.dto.kafka.CardInfoEventDto;
import com.iproddy.common.dto.kafka.OrderCreationStatus;
import com.iproddy.common.dto.kafka.OrderCreationStatusMessage;
import com.iproddy.paymentservice.model.entity.Payment;
import com.iproddy.paymentservice.model.vo.CardInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderCreationStatusMessageMapper {

    @Mappings({
            @Mapping(target = "paymentId", source = "payment.id"),
            @Mapping(target = "paymentMethod", source = "payment.method"),
            @Mapping(target = "cardInfo", source = "payment.cardInfo"),
            @Mapping(target = "status", source = "status")
    })
    OrderCreationStatusMessage toEvent(Payment payment, OrderCreationStatus status);

    CardInfoEventDto toEvent(CardInfo cardInfo);
}
