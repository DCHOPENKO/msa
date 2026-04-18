package com.iproddy.orderservice.job;

import com.iproddy.orderservice.kafla.producer.AsyncMessageProducer;
import com.iproddy.orderservice.model.entity.AsyncMessage;
import com.iproddy.orderservice.service.AsyncMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class AsyncMessageSendToKafkaJob {

    private final AsyncMessageProducer asyncMessageProducer;
    private final AsyncMessageService asyncMessageService;

    @Scheduled(fixedDelayString = "${job.transaction-outbox.queue-poll-delay-millis:3000}")
    public void sendEvents() {
        List<AsyncMessage> asyncMessages = asyncMessageService.getUnsentMessages(50);
        if (!asyncMessages.isEmpty()) {
            log.info("AsyncMessageSendToKafkaJob: found unsent events {} pcs. Starting to send", asyncMessages.size());
        }

        for (AsyncMessage asyncMessage : asyncMessages) {
            asyncMessageProducer.send(asyncMessage);
        }
    }
}
