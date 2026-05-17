package com.iproddy.orderservice.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "kafka.topic")
@Data
public class KafkaTopicProperties {

    private String orderCreationStatusTopic;
    private String orderCreateEventTopic;
    private String orderCancelEventTopic;
    private String paymentCreateEventTopic;
    private String paymentRefundEventTopic;
    private String deliveryCreateEventTopic;
}
