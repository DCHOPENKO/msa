package com.iproddy.paymentservice.service;

import com.iproddy.common.exception.NotFoundException;
import com.iproddy.paymentservice.model.entity.Payment;
import com.iproddy.paymentservice.model.enums.PaymentStatus;
import com.iproddy.paymentservice.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    @Transactional(readOnly = true)
    public List<Payment> findAll() {
        return paymentRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Payment findByIdOrThrow(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Payment with id: %s not found".formatted(id)));
    }

    public Payment save(Payment entity) {
        entity.setId(null);
        entity.setStatus(PaymentStatus.CREATED);
        return paymentRepository.save(entity);
    }

    public Payment update(Payment entity) {
        findByIdOrThrow(entity.getId());
        return paymentRepository.save(entity);
    }

    public void delete(Long id) {
        paymentRepository.deleteById(id);
    }
}