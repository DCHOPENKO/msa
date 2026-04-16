package com.iproddy.deliveryservice.mapper;

import com.iproddy.deliveryservice.kafka.producer.dto.DeliveryCreateEvent;
import com.iproddy.deliveryservice.model.entity.Delivery;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface DeliveryCreateEventMapper {

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "orderId", source = "orderId")
    })
    DeliveryCreateEvent toEvent(Delivery delivery);
}
