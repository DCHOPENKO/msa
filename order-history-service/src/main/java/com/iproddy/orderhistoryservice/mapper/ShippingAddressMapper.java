package com.iproddy.orderhistoryservice.mapper;

import com.iproddy.common.dto.kafka.ShippingAddressEventDto;
import com.iproddy.orderhistoryservice.model.dto.ShippingAddress;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ShippingAddressMapper {

    ShippingAddress from(ShippingAddressEventDto shippingAddress);

}
