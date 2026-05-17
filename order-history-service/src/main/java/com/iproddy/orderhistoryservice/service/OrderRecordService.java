package com.iproddy.orderhistoryservice.service;

import com.iproddy.orderhistoryservice.model.entity.OrderRecord;
import com.iproddy.orderhistoryservice.repository.OrderRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderRecordService {

    private final OrderRecordRepository orderRecordRepository;

    public OrderRecord save(OrderRecord orderRecord) {
        return orderRecordRepository.save(orderRecord);
    }

    public OrderRecord findByIdOrThrow(Long id) {
        return orderRecordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("OrderRecord with id: %s not found".formatted(id)));
    }

    public void setStatus(Long id, String status) {
        OrderRecord orderRecord = findByIdOrThrow(id);
        orderRecord.setStatus(status);
        orderRecordRepository.save(orderRecord);
    }


}
