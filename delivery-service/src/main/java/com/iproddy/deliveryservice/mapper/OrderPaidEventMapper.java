package com.iproddy.deliveryservice.mapper;

import com.iproddy.common.model.vo.CustomerInfo;
import com.iproddy.common.model.vo.ShippingAddress;
import com.iproddy.deliveryservice.kafka.consumer.dto.OrderPaidEvent;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface OrderPaidEventMapper {

    ShippingAddress toEntity(OrderPaidEvent.ShippingAddress shippingAddress);

    CustomerInfo toEntity(OrderPaidEvent.CustomerInfo customerInfo);
}
