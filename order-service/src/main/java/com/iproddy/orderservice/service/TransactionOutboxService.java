package com.iproddy.orderservice.service;

import com.iproddy.common.model.enums.OutboxMessageStatus;
import com.iproddy.orderservice.model.entity.TransactionOutbox;
import com.iproddy.orderservice.repository.TransactionOutboxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionOutboxService {

    private final TransactionOutboxRepository repository;

    public void saveMessage(TransactionOutbox message) {
        repository.save(message);
        log.info("Message saved to outbox with id: {}", message.getId().getId());
    }

    public List<TransactionOutbox> getUnsentOutboxMessages(int batchSize) {
        Pageable pageable = Pageable.ofSize(batchSize);
        return repository.findUnsentOutboxMessages(pageable);
    }

    public void markAsSent(TransactionOutbox message) {
        message.setStatus(OutboxMessageStatus.SENT);
        repository.save(message);
    }
}
