package com.iproddy.orderservice.repository;

import com.iproddy.orderservice.model.entity.TransactionOutboxMessageId;
import com.iproddy.orderservice.model.entity.TransactionOutbox;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionOutboxRepository extends JpaRepository<TransactionOutbox, TransactionOutboxMessageId> {

    @Query("SELECT t FROM TransactionOutbox t WHERE t.status = 'CREATED' AND t.type = 'OUTBOX' ORDER BY t.createdAt")
    List<TransactionOutbox> findUnsentOutboxMessages(Pageable pageable);

}
