package com.iproddy.orderhistoryservice.repository;

import com.iproddy.orderhistoryservice.model.entity.OrderRecord;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRecordRepository extends MongoRepository<OrderRecord, Long> {
}
