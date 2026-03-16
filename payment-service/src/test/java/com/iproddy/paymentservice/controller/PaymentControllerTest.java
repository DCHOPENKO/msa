package com.iproddy.paymentservice.controller;

import com.iproddy.paymentservice.IntegrationTestBase;
import com.iproddy.paymentservice.model.entity.Payment;
import com.iproddy.paymentservice.model.enums.PaymentStatus;
import com.iproddy.paymentservice.util.TestDataFactory;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PaymentControllerTest extends IntegrationTestBase {

    @Test
    void createPayment_shouldPersistPaymentAndReturnResponse() throws Exception {
        var request = TestDataFactory.createPaymentBaseRequest();

        mockMvc.perform(post("/api/v1/payments")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.status").value(PaymentStatus.CREATED.name()))
                .andExpect(jsonPath("$.cardInfo.cardNumber").value(request.cardInfo().cardNumber()))
                .andExpect(jsonPath("$.amount").value(request.amount()));
    }

    @Test
    public void updatePayment_shouldUpdatePaymentAndReturnResponse() throws Exception {
        Payment payment = initPayment();

        assertThat(paymentRepository.findAll()).hasSize(1);

        var request = TestDataFactory.createPaymentBaseRequest();

        mockMvc.perform(put("/api/v1/payments/{id}", payment.getId())
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.status").value(request.status().name()))
                .andExpect(jsonPath("$.cardInfo.cardNumber").value(request.cardInfo().cardNumber()))
                .andExpect(jsonPath("$.amount").value(request.amount()));

        List<Payment> allPayments = paymentRepository.findAll();
        assertThat(allPayments).hasSize(1);
        Payment updatedPayment = allPayments.getFirst();
        assertThat(updatedPayment.getId()).isEqualTo(payment.getId());
        assertThat(updatedPayment.getCardInfo().getCardHolder()).isNotEqualTo(payment.getCardInfo().getCardHolder());
        assertThat(updatedPayment.getAmount()).isNotEqualTo(payment.getAmount());
    }

    @Test
    void deletePayment_shouldRemovePayment() throws Exception {
        Payment payment = initPayment();
        long id = payment.getId();

        assertThat(paymentRepository.findById(id)).isNotEmpty();

        mockMvc.perform(delete("/api/v1/payments/{id}", id))
                .andExpect(status().isOk());

        assertThat(paymentRepository.findById(id)).isEmpty();
    }

    @Test
    void getPaymentById_shouldReturnSavedPayment() throws Exception {
        Payment payment = initPayment();
        long id = payment.getId();

        assertThat(paymentRepository.findById(id)).isNotEmpty();

        mockMvc.perform(get("/api/v1/payments/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id));
    }

    @Test
    void getAllPayments_shouldReturnAllSavedPayments() throws Exception {
        initPayment();
        initPayment();

        mockMvc.perform(get("/api/v1/payments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    private Payment initPayment() {
        Payment payment = TestDataFactory.createPayment();
        return paymentRepository.save(payment);
    }
}
