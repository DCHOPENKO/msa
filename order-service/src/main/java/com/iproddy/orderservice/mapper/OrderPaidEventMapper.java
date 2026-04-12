package com.iproddy.orderservice.mapper;

import com.iproddy.common.model.vo.CustomerInfo;
import com.iproddy.common.model.vo.ShippingAddress;
import com.iproddy.orderservice.kafla.producer.dto.OrderPaidEvent;
import com.iproddy.orderservice.model.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface OrderPaidEventMapper {

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "shippingAddress", source = "shippingAddress"),
            @Mapping(target = "customerInfo", source = "customerInfo")
    })
    OrderPaidEvent toEvent(Order order);

    OrderPaidEvent.ShippingAddress toEvent(ShippingAddress shippingAddress);

    OrderPaidEvent.CustomerInfo toEvent(CustomerInfo customerInfo);
}
