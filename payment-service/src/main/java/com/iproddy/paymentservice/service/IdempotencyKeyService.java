package com.iproddy.paymentservice.service;

import com.iproddy.common.exception.IdempotencyKeyExistsException;
import com.iproddy.common.exception.NotFoundException;
import com.iproddy.paymentservice.model.entity.IdempotencyKey;
import com.iproddy.paymentservice.model.enums.IdempotencyKeyStatus;
import com.iproddy.paymentservice.repository.IdempotencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class IdempotencyKeyService {

    private final IdempotencyRepository repository;

    public void create(Long key) {
        IdempotencyKey idempotencyKey = new IdempotencyKey();
        idempotencyKey.setKey(key);
        idempotencyKey.setStatus(IdempotencyKeyStatus.PENDING);
        try {
            repository.save(idempotencyKey);
        } catch (DataIntegrityViolationException e) {
            throw new IdempotencyKeyExistsException("Key already exists", e);
        }
    }

    @Transactional(readOnly = true)
    public Optional<IdempotencyKey> findById(Long key) {
        return repository.findById(key);
    }

    @Transactional(readOnly = true)
    public IdempotencyKey findByIdOrThrow(Long key) {
        return repository.findById(key).orElseThrow(() -> new NotFoundException("IdempotencyKey with id %S not found"));
    }

    public void markAsCompleted(Long key, String responseData, int statusCode) {
        var keyEntity = findByIdOrThrow(key);
        keyEntity.setStatus(IdempotencyKeyStatus.COMPLETED);
        keyEntity.setStatusCode(statusCode);
        keyEntity.setResponse(responseData);
        repository.save(keyEntity);
    }
}
