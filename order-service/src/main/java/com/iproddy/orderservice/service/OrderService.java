package com.iproddy.orderservice.service;

import com.iproddy.common.exception.NotFoundException;
import com.iproddy.orderservice.model.entity.Order;
import com.iproddy.orderservice.model.enums.OrderStatus;
import com.iproddy.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    @Transactional(readOnly = true)
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Order findByIdOrThrow(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order with id: %s not found".formatted(id)));
    }

    @Transactional(readOnly = true)
    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    public Order save(Order entity) {
        entity.setId(null);
        entity.setStatus(OrderStatus.CREATED);
        entity.replaceItems(entity.getItems());
        return orderRepository.save(entity);
    }

    public Order update(Order entity) {
        Order order = findByIdOrThrow(entity.getId());
        if (order.getStatus() == OrderStatus.CREATED) {
            order.replaceItems(entity.getItems());
        } else {
            log.info("For Order with id: %s updating shopping cart will be ignored. Order already in processing with status: %s"
                    .formatted(order.getId(), order.getStatus()));
        }
        order.setCustomerInfo(entity.getCustomerInfo());
        order.setShippingAddress(entity.getShippingAddress());
        order.setStatus(entity.getStatus());
        return orderRepository.save(order);
    }

    public void delete(Long id) {
        orderRepository.deleteById(id);
    }

    public void setPaymentId(Order entity, Long paymentId) {
        if (paymentId == null || Objects.equals(paymentId, entity.getPaymentId())) {
            return;
        }
        entity.setPaymentId(paymentId);
        entity.setStatus(OrderStatus.PAYMENT_PROCESSING);
        orderRepository.save(entity);
    }
}
