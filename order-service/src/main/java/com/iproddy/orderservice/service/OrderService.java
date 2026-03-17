package com.iproddy.orderservice.service;

import com.iproddy.common.exception.NotFoundException;
import com.iproddy.orderservice.model.entity.Order;
import com.iproddy.common.model.enums.OrderStatus;
import com.iproddy.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    public Order save(Order entity) {
        entity.setId(null);
        entity.setStatus(OrderStatus.NEW);
        entity.replaceItems(entity.getItems());
        return orderRepository.save(entity);
    }

    public Order update(Order entity) {
        Order order = findByIdOrThrow(entity.getId());
        order.setCustomerInfo(entity.getCustomerInfo());
        order.setShippingAddress(entity.getShippingAddress());
        order.setStatus(entity.getStatus());
        order.replaceItems(entity.getItems());
        return orderRepository.save(order);
    }

    public void delete(Long id) {
        orderRepository.deleteById(id);
    }
}
