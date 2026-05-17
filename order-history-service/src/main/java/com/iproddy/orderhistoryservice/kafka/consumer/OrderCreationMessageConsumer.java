package com.iproddy.orderhistoryservice.kafka.consumer;

import com.iproddy.common.dto.kafka.OrderCreationStatus;
import com.iproddy.common.dto.kafka.OrderCreationStatusMessage;
import com.iproddy.orderhistoryservice.mapper.CardInfoMapper;
import com.iproddy.orderhistoryservice.mapper.CustomerInfoMapper;
import com.iproddy.orderhistoryservice.mapper.OrderItemMapper;
import com.iproddy.orderhistoryservice.mapper.ShippingAddressMapper;
import com.iproddy.orderhistoryservice.model.entity.OrderRecord;
import com.iproddy.orderhistoryservice.service.OrderRecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderCreationMessageConsumer {

    private final OrderRecordService orderRecordService;
    private final ShippingAddressMapper shippingAddressMapper;
    private final CustomerInfoMapper customerInfoMapper;
    private final CardInfoMapper cardInfoMapper;
    private final OrderItemMapper orderItemMapper;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @KafkaListener(topics = "${kafka.topic.order-creation-status-topic}", groupId = "order-history")
    public void handle(OrderCreationStatusMessage message) {
        Long orderId = message.orderId();
        OrderCreationStatus status = message.status();
        log.info("Received event for orderId: {}, status: {}", orderId, status);
        switch (status) {
            case DRAFT -> createOrderRecord(message);
            case PAYMENT_COMPLETED -> addPaymentDataToOrderRecord(message);
            case DELIVERY_COMPLETED -> addDeliveryDataToOrderRecord(message);
            default -> {}
        }

        orderRecordService.setStatus(orderId, status.name());
        log.info("Status updated to: {}", status.name());
    }

    private void createOrderRecord(OrderCreationStatusMessage message) {
        OrderRecord orderRecord = new OrderRecord();
        orderRecord.setId(message.orderId());
        orderRecord.setOrderItems(orderItemMapper.from(message.orderItems()));
        orderRecord.setTotalAmount(message.totalAmount());
        orderRecordService.save(orderRecord);
        log.info("Created new OrderRecord for DRAFT with Id: {}", orderRecord.getId());
    }

    private void addPaymentDataToOrderRecord(OrderCreationStatusMessage message) {
        OrderRecord orderRecord = orderRecordService.findByIdOrThrow(message.orderId());
        orderRecord.setPaymentId(message.paymentId());
        orderRecord.setPaymentMethod(message.paymentMethod().name());
        orderRecord.setCardInfo(cardInfoMapper.from(message.cardInfo()));
        orderRecordService.save(orderRecord);
        log.info("Updated Payment data for OrderRecord with Id: {}", orderRecord.getId());
    }

    private void addDeliveryDataToOrderRecord(OrderCreationStatusMessage message) {
        OrderRecord orderRecord = orderRecordService.findByIdOrThrow(message.orderId());
        orderRecord.setDeliveryId(message.orderId());
        orderRecord.setShippingAddress(shippingAddressMapper.from(message.shippingAddress()));
        orderRecord.setCustomerInfo(customerInfoMapper.from(message.customerInfo()));
        orderRecordService.save(orderRecord);
        log.info("Updated Delivery data for OrderRecord with Id: {}", orderRecord.getId());
    }

}