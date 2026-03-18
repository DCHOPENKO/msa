package com.iproddy.paymentservice.controller;

import com.iproddy.common.util.JsonUtil;
import com.iproddy.paymentservice.controller.dto.PaymentDto;
import com.iproddy.paymentservice.mapper.PaymentMapper;
import com.iproddy.paymentservice.model.entity.Payment;
import com.iproddy.paymentservice.service.PaymentService;
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
@RequestMapping("api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final PaymentMapper paymentMapper;

    @GetMapping
    public List<PaymentDto.Response.Base> findAll() {
        List<Payment> payments = paymentService.findAll();
        return paymentMapper.toResponseList(payments);
    }

    @GetMapping("/{id}")
    public PaymentDto.Response.Base findById(@PathVariable Long id) {
        Payment payment = paymentService.findByIdOrThrow(id);
        return paymentMapper.toResponse(payment);
    }

    @PostMapping
    public PaymentDto.Response.Base create(@RequestBody PaymentDto.Request.Base request) {
        log.info("Starting create new payment with payload: {}", JsonUtil.stringify(request));
        Payment payment = paymentMapper.toEntity(request);
        Payment saved = paymentService.save(payment);
        log.info("Payment created with id {}", saved.getId());
        return paymentMapper.toResponse(saved);
    }

    @PutMapping("/{id}")
    public PaymentDto.Response.Base update(@PathVariable Long id, @RequestBody PaymentDto.Request.Base request) {
        log.info("Starting updating payment with id: {}, payload: {}", id, JsonUtil.stringify(request));
        Payment payment = paymentMapper.toEntity(request);
        payment.setId(id);
        Payment updated = paymentService.update(payment);
        log.info("Payment updated with id {}", updated.getId());
        return paymentMapper.toResponse(updated);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        log.info("Attempt to delete payment with id: {}", id);
        paymentService.delete(id);
    }
}
