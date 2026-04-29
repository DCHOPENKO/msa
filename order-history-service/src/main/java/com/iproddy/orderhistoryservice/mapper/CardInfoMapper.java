package com.iproddy.orderhistoryservice.mapper;

import com.iproddy.common.dto.kafka.CardInfoEventDto;
import com.iproddy.orderhistoryservice.model.dto.CardInfo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface CardInfoMapper {

    CardInfo from(CardInfoEventDto cardInfo);
}
