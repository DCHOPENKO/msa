package com.iproddy.orderservice.controller;

import com.iproddy.common.util.JsonUtil;
import com.iproddy.orderservice.controller.dto.OrderDto;
import com.iproddy.orderservice.http.client.payment.PaymentClient;
import com.iproddy.orderservice.http.client.payment.dto.PaymentDto;
import com.iproddy.orderservice.mapper.OrderMapper;
import com.iproddy.orderservice.mapper.PaymentCreateRequestEventMapper;
import com.iproddy.orderservice.mapper.PaymentDtoMapper;
import com.iproddy.orderservice.model.entity.Order;
import com.iproddy.orderservice.rabbitmq.producer.PaymentCreateEventProducer;
import com.iproddy.orderservice.rabbitmq.producer.dto.PaymentCreateRequestEvent;
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
    private final PaymentClient paymentClient;
    private final PaymentCreateEventProducer paymentCreateEventProducer;
    private final PaymentDtoMapper paymentDtoMapper;
    private final PaymentCreateRequestEventMapper paymentCreateRequestEventMapper;

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

    @Override
    @PostMapping("/sync-integration")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto.Response.Base createWithSyncIntegration(@RequestBody OrderDto.Request.Create request) {
        log.info("Starting create new order (sync integration) with payload: {}", JsonUtil.stringify(request));
        Order order = orderMapper.toEntity(request);
        Order saved = orderService.save(order);
        PaymentDto.Response.Base paymentResponse = null;
        if (request.paymentMethod() != null) {
            log.info("Executing payment creation process for order with id: {}", saved.getId());
            PaymentDto.Request.Base paymentRequest = paymentDtoMapper.toRequest(saved, request);
            paymentResponse = paymentClient.createPayment(paymentRequest);
            orderService.setPaymentId(saved, paymentResponse.id());
            log.info("Payment creation process finished for order with id: {}, paymentId: {}", saved.getId(), paymentResponse.id());
        }
        log.info("Order created (sync integration) with id {}", saved.getId());
        return orderMapper.toResponse(saved);
    }

    @PostMapping("/async-integration")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto.Response.Base createWithASyncIntegration(@RequestBody OrderDto.Request.Create request) {
        log.info("Starting create new order (async rabbit integration) with payload: {}", JsonUtil.stringify(request));
        Order order = orderMapper.toEntity(request);
        Order saved = orderService.save(order);
        if (request.paymentMethod() != null) {
            PaymentCreateRequestEvent paymentEvent = paymentCreateRequestEventMapper.toEvent(saved, request);
            paymentCreateEventProducer.send(paymentEvent);
        }
        log.info("Order created (async rabbit integration) with id {}", saved.getId());
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
