package com.iproddy.paymentservice.repository;

import com.iproddy.paymentservice.model.entity.IdempotencyKey;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.jspecify.annotations.NonNull;

import java.util.Optional;

public interface IdempotencyRepository extends JpaRepository<IdempotencyKey, Long> {

    @Override
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @NonNull
    Optional<IdempotencyKey> findById(@NonNull Long key);
}
