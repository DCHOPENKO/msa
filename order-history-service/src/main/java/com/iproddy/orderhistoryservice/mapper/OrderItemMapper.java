package com.iproddy.orderhistoryservice.mapper;

import com.iproddy.common.dto.kafka.OrderItemEventDto;
import com.iproddy.orderhistoryservice.model.dto.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface OrderItemMapper {

    OrderItem from(OrderItemEventDto orderItem);

    List<OrderItem> from(List<OrderItemEventDto> orderItems);
}
