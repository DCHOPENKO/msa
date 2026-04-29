package com.iproddy.orderservice.saga;

import com.iproddy.common.dto.kafka.OrderCreationStatus;
import com.iproddy.common.dto.kafka.OrderCreationStatusMessage;
import com.iproddy.common.dto.kafka.PaymentCreationMessage;
import com.iproddy.common.dto.kafka.PaymentRefundingMessage;
import com.iproddy.orderservice.config.properties.KafkaTopicProperties;
import com.iproddy.orderservice.kafla.producer.OrderCreationStatusMessageProducer;
import com.iproddy.orderservice.mapper.DeliveryCreationMapper;
import com.iproddy.orderservice.model.entity.Order;
import com.iproddy.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderCreationSagaOrchestrator {

    private final OrderService orderService;
    private final DeliveryCreationMapper deliveryCreationMapper;
    private final KafkaTopicProperties kafkaTopicProperties;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final OrderCreationStatusMessageProducer orderCreationStatusMessageProducer;

    @KafkaListener(topics = "${kafka.topic.order-creation-status-topic}", groupId = "saga")
    public void handleSagaEvents(OrderCreationStatusMessage message) {
        long orderId = message.orderId();
        OrderCreationStatus status = message.status();

        log.info("Received saga event for orderId: {}, status: {}", orderId, status);

        switch (status) {
            case DRAFT -> {
                log.info("Handling DRAFT status, init payment process for orderId: {}", orderId);
                sendPaymentCreateMessage(message, orderId);
            }
            case PAYMENT_COMPLETED -> {
                log.info("Payment process completed, init delivery process for orderId: {}", orderId);
                sendDeliveryCreateMessage(orderId);
            }
            case DELIVERY_COMPLETED -> {
                log.info("Delivery process completed, sending complete order message for orderId: {}", orderId);
                sendCompleteOrderMessage(orderId);
            }
            case PAYMENT_FAILED -> {
                log.info("Payment failed, sending cancel order for orderId: {}", orderId);
                sendCancelOrderMessage(orderId);
            }
            case DELIVERY_CANCELLED -> {
                log.warn("Delivery cancelled for orderId: {}, init refunding process and cancellation order", orderId);
                sendPaymentRefundMessage(orderId);
                sendCancelOrderMessage(orderId);
            }
            default -> log.warn("Unknown status: {} for appointmentId: {}", status, orderId);
        }
    }

    private void sendPaymentCreateMessage(OrderCreationStatusMessage message,
                                          long orderId) {
        var paymentCreationMessage = PaymentCreationMessage.builder()
                .orderId(orderId)
                .paymentMethod(message.paymentMethod())
                .totalAmount(message.totalAmount())
                .cardInfo(message.cardInfo())
                .build();
        kafkaTemplate.send(kafkaTopicProperties.getPaymentCreateEventTopic(), paymentCreationMessage);
    }

    private void sendPaymentRefundMessage(long orderId) {
        var paymentCreationMessage = new PaymentRefundingMessage(orderId);
        kafkaTemplate.send(kafkaTopicProperties.getPaymentRefundEventTopic(), paymentCreationMessage);
    }

    private void sendDeliveryCreateMessage(long orderId) {
        Order order = orderService.findByIdOrThrow(orderId);
        var deliveryCreationMessage = deliveryCreationMapper.toMessage(order);
        kafkaTemplate.send(kafkaTopicProperties.getDeliveryCreateEventTopic(), deliveryCreationMessage);
    }

    private void sendCancelOrderMessage(long orderId) {
        var message = OrderCreationStatusMessage.builder()
                .orderId(orderId)
                .status(OrderCreationStatus.CANCELLED)
                .build();
        orderCreationStatusMessageProducer.send(message);
    }

    private void sendCompleteOrderMessage(long orderId) {
        var message = OrderCreationStatusMessage.builder()
                .orderId(orderId)
                .status(OrderCreationStatus.COMPLETED)
                .build();
        orderCreationStatusMessageProducer.send(message);
    }
}