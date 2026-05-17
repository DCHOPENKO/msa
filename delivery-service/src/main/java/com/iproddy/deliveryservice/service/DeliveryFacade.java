package com.iproddy.deliveryservice.service;

import com.iproddy.common.dto.kafka.DeliveryCreationMessage;
import com.iproddy.common.dto.kafka.OrderCreationStatus;
import com.iproddy.common.dto.kafka.OrderCreationStatusMessage;
import com.iproddy.deliveryservice.kafka.producer.OrderCreationStatusMessageProducer;
import com.iproddy.deliveryservice.mapper.DeliveryMapper;
import com.iproddy.deliveryservice.mapper.OrderCreationStatusMessageMapper;
import com.iproddy.deliveryservice.model.entity.Delivery;
import com.iproddy.deliveryservice.model.enums.DeliveryStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Random;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeliveryFacade {

    private final DeliveryService deliveryService;
    private final DeliveryMapper deliveryMapper;
    private final OrderCreationStatusMessageMapper orderCreationStatusMapper;
    private final OrderCreationStatusMessageProducer orderCreationStatusMessageProducer;
    private final Random random = new Random();

    public void proceedShipment(DeliveryCreationMessage message) throws InterruptedException {

        log.info("Create new shipment for order with id: {}", message.orderId());
        Delivery entity = deliveryMapper.toEntity(message);
        Delivery saved = deliveryService.save(entity);
        log.info("Shipment with id {} for order (id: {}) created. Starting Shipping process",saved.getId(), message.orderId());

        // имитация доставки
        Thread.sleep(5000);
        boolean isTrue = random.nextBoolean();
        DeliveryStatus deliveryStatus = isTrue ? DeliveryStatus.DELIVERED : DeliveryStatus.CANCELLED;
        OrderCreationStatus orderCreationStatus = isTrue ? OrderCreationStatus.DELIVERY_COMPLETED : OrderCreationStatus.DELIVERY_CANCELLED;

        deliveryService.setStatus(saved, deliveryStatus);
        sendOrderCreationStatusMessage(saved, orderCreationStatus);
        log.info("Shipment with id {} for order (id: {}) was {}.",saved.getId(), message.orderId(), isTrue ? "delivered" : "cancelled");
    }

    private void sendOrderCreationStatusMessage(Delivery delivery, OrderCreationStatus status) {
        OrderCreationStatusMessage message = orderCreationStatusMapper.toEvent(delivery, status);
        orderCreationStatusMessageProducer.send(message);
    }
}
