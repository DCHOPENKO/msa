package com.iproddy.orderservice.repository;

import com.iproddy.orderservice.model.entity.AsyncMessageId;
import com.iproddy.orderservice.model.entity.AsyncMessage;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AsyncMessageRepository extends JpaRepository<AsyncMessage, AsyncMessageId> {

    @Query("SELECT t FROM AsyncMessage t WHERE t.status = 'CREATED' AND t.type = 'OUTBOX' ORDER BY t.createdAt")
    List<AsyncMessage> findUnsentOutboxMessages(Pageable pageable);

}
