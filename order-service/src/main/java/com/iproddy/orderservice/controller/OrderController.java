package com.iproddy.orderservice.controller;

import com.iproddy.common.util.JsonUtil;
import com.iproddy.orderservice.controller.dto.OrderDto;
import com.iproddy.orderservice.kafla.producer.OrderCreationStatusMessageProducer;
import com.iproddy.common.dto.kafka.OrderCreationStatusMessage;
import com.iproddy.orderservice.mapper.OrderCreationStatusMessageMapper;
import com.iproddy.orderservice.mapper.OrderMapper;
import com.iproddy.orderservice.model.entity.Order;
import com.iproddy.orderservice.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/orders")
@RequiredArgsConstructor
@CircuitBreaker(name = "orderControllerCircuitBreaker")
public class OrderController implements OrderControllerDoc {

    private final OrderService orderService;
    private final OrderMapper orderMapper;
    private final OrderCreationStatusMessageProducer orderCreationStatusMessageProducer;
    private final OrderCreationStatusMessageMapper orderCreationStatusMessageMapper;


    @Override
    @GetMapping
    public List<OrderDto.Response.Base> findAll() {
        List<Order> orders = orderService.findAll();
        return orderMapper.toResponseList(orders);
    }

    @Override
    @GetMapping("/{id}")
    public OrderDto.Response.Base findById(@PathVariable Long id) {
        Order order = orderService.findByIdOrThrow(id);
        return orderMapper.toResponse(order);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto.Response.Base createWithASyncIntegration(@RequestBody OrderDto.Request.Create request) {
        log.info("Starting create new order with payload: {}", JsonUtil.stringify(request));
        Order order = orderMapper.toEntity(request);
        Order saved = orderService.save(order);
        if (request.paymentMethod() != null) {
            OrderCreationStatusMessage message = orderCreationStatusMessageMapper.toEvent(saved, request);
            orderCreationStatusMessageProducer.send(message);
        }
        log.info("Order created with id {}", saved.getId());
        return orderMapper.toResponse(saved);
    }

    @Override
    @PutMapping("/{id}")
    public OrderDto.Response.Base update(@PathVariable Long id, @RequestBody OrderDto.Request.Update request) {
        log.info("Starting updating order with id: {}, payload: {}", id, JsonUtil.stringify(request));
        Order order = orderMapper.toEntity(request);
        order.setId(id);
        Order updated = orderService.update(order);
        log.info("Order updated with id {}", updated.getId());
        return orderMapper.toResponse(updated);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        log.info("Attempt to delete order with id: {}", id);
        orderService.delete(id);
    }
}
