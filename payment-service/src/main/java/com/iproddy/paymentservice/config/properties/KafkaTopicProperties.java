package com.iproddy.paymentservice.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "kafka.topic")
@Data
public class KafkaTopicProperties {

    private String orderCreationStatusTopic;
}
