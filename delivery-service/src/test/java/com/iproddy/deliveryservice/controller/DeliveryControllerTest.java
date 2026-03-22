package com.iproddy.deliveryservice.controller;

import com.iproddy.deliveryservice.IntegrationTestBase;
import com.iproddy.deliveryservice.model.entity.Delivery;
import com.iproddy.deliveryservice.model.enums.DeliveryStatus;
import com.iproddy.deliveryservice.util.TestDataFactory;
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

public class DeliveryControllerTest extends IntegrationTestBase {

    @Test
    void createDelivery_shouldPersistDeliveryAndReturnResponse() throws Exception {
        var request = TestDataFactory.createDeliveryBaseRequest();

        mockMvc.perform(post("/api/v1/deliveries")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.status").value(DeliveryStatus.CREATED.name()))
                .andExpect(jsonPath("$.customerInfo.customerName").value(request.customerInfo().customerName()))
                .andExpect(jsonPath("$.shippingAddress.city").value(request.shippingAddress().city()))
                .andExpect(jsonPath("$.orderId").value(request.orderId()));
    }

    @Test
    public void updateDelivery_shouldUpdateDeliveryAndReturnResponse() throws Exception {
        Delivery delivery = initDelivery();

        assertThat(deliveryRepository.findAll()).hasSize(1);

        var request = TestDataFactory.createDeliveryBaseRequest();

        mockMvc.perform(put("/api/v1/deliveries/{id}", delivery.getId())
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.status").value(request.status().name()))
                .andExpect(jsonPath("$.customerInfo.customerName").value(request.customerInfo().customerName()))
                .andExpect(jsonPath("$.shippingAddress.city").value(request.shippingAddress().city()))
                .andExpect(jsonPath("$.orderId").value(request.orderId()));

        List<Delivery> allDeliveries = deliveryRepository.findAll();
        assertThat(allDeliveries).hasSize(1);
        Delivery updatedDelivery = allDeliveries.getFirst();
        assertThat(updatedDelivery.getId()).isEqualTo(delivery.getId());
        assertThat(updatedDelivery.getCustomerInfo().getEmail()).isNotEqualTo(delivery.getCustomerInfo().getEmail());
        assertThat(updatedDelivery.getShippingAddress().getStreet()).isNotEqualTo(delivery.getShippingAddress().getStreet());
        assertThat(updatedDelivery.getOrderId()).isNotEqualTo(delivery.getOrderId());
    }

    @Test
    void deleteDelivery_shouldRemoveDelivery() throws Exception {
        Delivery delivery = initDelivery();
        long id = delivery.getId();

        assertThat(deliveryRepository.findById(id)).isNotEmpty();

        mockMvc.perform(delete("/api/v1/deliveries/{id}", id))
                .andExpect(status().isNoContent());

        assertThat(deliveryRepository.findById(id)).isEmpty();
    }

    @Test
    void getDeliveryById_shouldReturnSavedDelivery() throws Exception {
        Delivery delivery = initDelivery();
        long id = delivery.getId();

        assertThat(deliveryRepository.findById(id)).isNotEmpty();

        mockMvc.perform(get("/api/v1/deliveries/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id));
    }

    @Test
    void getAllDeliveries_shouldReturnAllSavedDeliveries() throws Exception {
        initDelivery();
        initDelivery();

        mockMvc.perform(get("/api/v1/deliveries"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    private Delivery initDelivery() {
        Delivery payment = TestDataFactory.createDelivery();
        return deliveryRepository.save(payment);
    }
}
