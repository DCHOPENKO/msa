package com.iproddy.orderservice.job;

import com.iproddy.orderservice.kafla.producer.TransactionOutboxProducer;
import com.iproddy.orderservice.model.entity.TransactionOutbox;
import com.iproddy.orderservice.service.TransactionOutboxService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class TransactionOutboxSendToKafkaJob {

    private final TransactionOutboxProducer transactionOutboxProducer;
    private final TransactionOutboxService transactionOutboxService;

    @Scheduled(fixedDelayString = "${job.transaction-outbox.queue-poll-delay-millis:3000}")
    public void sendEvents() {
        List<TransactionOutbox> transactionOutboxes = transactionOutboxService.getUnsentOutboxMessages(50);
        log.info("TransactionOutboxSendToKafkaJob: found unsent events {} pcs. {}",
                transactionOutboxes.size(), transactionOutboxes.isEmpty() ? "" : "Starting to send");
        for (TransactionOutbox transactionOutbox : transactionOutboxes) {
            transactionOutboxProducer.send(transactionOutbox);
        }
    }
}
