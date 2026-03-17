package com.iproddy.orderservice.controller;

import com.iproddy.common.util.JsonUtil;
import com.iproddy.orderservice.controller.dto.OrderDto;
import com.iproddy.orderservice.mapper.OrderMapper;
import com.iproddy.orderservice.model.entity.Order;
import com.iproddy.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @GetMapping
    public List<OrderDto.Response.Base> findAll() {
        List<Order> orders = orderService.findAll();
        return orderMapper.toResponseList(orders);
    }

    @GetMapping("/{id}")
    public OrderDto.Response.Base findById(@PathVariable Long id) {
        Order order = orderService.findByIdOrThrow(id);
        return orderMapper.toResponse(order);
    }

    @PostMapping
    public OrderDto.Response.Base create(@RequestBody OrderDto.Request.Base request) {
        log.info("Starting create new order with payload: {}", JsonUtil.stringify(request));
        Order order = orderMapper.toEntity(request);
        Order saved = orderService.save(order);
        log.info("Order created with id {}", saved.getId());
        return orderMapper.toResponse(saved);
    }

    @PutMapping("/{id}")
    public OrderDto.Response.Base update(@PathVariable Long id, @RequestBody OrderDto.Request.Base request) {
        log.info("Starting updating order with id: {}, payload: {}", id, JsonUtil.stringify(request));
        Order order = orderMapper.toEntity(request);
        order.setId(id);
        Order updated = orderService.update(order);
        log.info("Order updated with id {}", updated.getId());
        return orderMapper.toResponse(updated);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        log.info("Attempt to delete order with id: {}", id);
        orderService.delete(id);
    }
}
