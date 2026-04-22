package com.iproddy.orderservice.service;

import com.iproddy.orderservice.model.enums.AsyncMessageStatus;
import com.iproddy.orderservice.model.entity.AsyncMessage;
import com.iproddy.orderservice.repository.AsyncMessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AsyncMessageService {

    private final AsyncMessageRepository repository;

    public void save(AsyncMessage message) {
        repository.save(message);
        log.info("Message saved to outbox with id: {}", message.getId().getId());
    }

    public List<AsyncMessage> getUnsentMessages(int batchSize) {
        Pageable pageable = Pageable.ofSize(batchSize);
        return repository.findUnsentOutboxMessages(pageable);
    }

    public void markAsSent(AsyncMessage message) {
        message.setStatus(AsyncMessageStatus.SENT);
        repository.save(message);
    }
}
