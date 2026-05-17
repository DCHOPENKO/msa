package com.iproddy.deliveryservice.mapper;

import com.iproddy.common.dto.kafka.CustomerInfoEventDto;
import com.iproddy.common.dto.kafka.OrderCreationStatus;
import com.iproddy.common.dto.kafka.OrderCreationStatusMessage;
import com.iproddy.common.dto.kafka.ShippingAddressEventDto;
import com.iproddy.common.model.vo.CustomerInfo;
import com.iproddy.common.model.vo.ShippingAddress;
import com.iproddy.deliveryservice.model.entity.Delivery;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderCreationStatusMessageMapper {

    @Mappings({
            @Mapping(target = "deliveryId", source = "delivery.id"),
            @Mapping(target = "shippingAddress", source = "delivery.shippingAddress"),
            @Mapping(target = "customerInfo", source = "delivery.customerInfo"),
            @Mapping(target = "status", source = "status")
    })
    OrderCreationStatusMessage toEvent(Delivery delivery, OrderCreationStatus status);

    ShippingAddressEventDto toEvent(ShippingAddress shippingAddress);

    CustomerInfoEventDto toEvent(CustomerInfo customerInfo);
}
