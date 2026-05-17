package com.iproddy.orderhistoryservice.mapper;

import com.iproddy.common.dto.kafka.CustomerInfoEventDto;
import com.iproddy.orderhistoryservice.model.dto.CustomerInfo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface CustomerInfoMapper {

    CustomerInfo from(CustomerInfoEventDto customerInfo);

}
