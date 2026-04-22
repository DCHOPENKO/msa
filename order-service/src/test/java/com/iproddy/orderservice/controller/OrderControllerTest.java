package com.iproddy.orderservice.controller;

import com.iproddy.orderservice.IntegrationTestBase;
import com.iproddy.orderservice.controller.dto.OrderItemDto;
import com.iproddy.orderservice.model.entity.Order;
import com.iproddy.orderservice.model.enums.OrderStatus;
import com.iproddy.orderservice.util.TestDataFactory;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class OrderControllerTest extends IntegrationTestBase {

    @Test
    public void updateOrder_shouldUpdateOrderAndReturnResponse() throws Exception {
        Order order = initOrder(false);


        assertThat(orderRepository.findAll()).hasSize(1);

        var request = TestDataFactory.createOrderUpdateRequest();
        BigDecimal totalAmount = calculateTotalAmount(request.items());

        mockMvc.perform(put("/api/v1/orders/{id}", order.getId())
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.status").value(request.status().name()))
                .andExpect(jsonPath("$.customerInfo.customerName").value(request.customerInfo().customerName()))
                .andExpect(jsonPath("$.shippingAddress.city").value(request.shippingAddress().city()))
                .andExpect(jsonPath("$.items.length()").value(request.items().size()))
                .andExpect(jsonPath("$.totalAmount").value(totalAmount));

        List<Order> allOrders = orderRepository.findAll();
        assertThat(allOrders).hasSize(1);
        Order updatedOrder = allOrders.getFirst();
        assertThat(updatedOrder.getId()).isEqualTo(order.getId());
        assertThat(updatedOrder.getShippingAddress().getCity()).isNotEqualTo(order.getShippingAddress().getCity());
        assertThat(updatedOrder.getCustomerInfo().getCustomerName()).isNotEqualTo(order.getCustomerInfo().getCustomerName());
        assertThat(updatedOrder.getTotalAmount()).isNotEqualTo(order.getTotalAmount());
    }

    @Test
    public void updateOrderWithPayment_shouldUpdateOrderWithoutOrderItemsAndReturnResponse() throws Exception {
        Order order = initOrder(true);

        assertThat(orderRepository.findAll()).hasSize(1);

        var request = TestDataFactory.createOrderUpdateRequest();
        BigDecimal totalAmount = calculateTotalAmount(request.items());

        mockMvc.perform(put("/api/v1/orders/{id}", order.getId())
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.status").value(request.status().name()))
                .andExpect(jsonPath("$.customerInfo.customerName").value(request.customerInfo().customerName()))
                .andExpect(jsonPath("$.shippingAddress.city").value(request.shippingAddress().city()))
                .andExpect(jsonPath("$.items.length()").value(order.getItems().size()))
                .andExpect(jsonPath("$.totalAmount").value(order.getTotalAmount()));

        List<Order> allOrders = orderRepository.findAll();
        assertThat(allOrders).hasSize(1);
        Order updatedOrder = allOrders.getFirst();
        assertThat(updatedOrder.getId()).isEqualTo(order.getId());
        assertThat(updatedOrder.getShippingAddress().getCity()).isNotEqualTo(order.getShippingAddress().getCity());
        assertThat(updatedOrder.getCustomerInfo().getCustomerName()).isNotEqualTo(order.getCustomerInfo().getCustomerName());
        assertThat(updatedOrder.getTotalAmount()).isNotEqualTo(totalAmount);
    }

    @Test
    void deleteOrder_shouldRemoveOrder() throws Exception {
        Order order = initOrder(false);
        long id = order.getId();

        assertThat(orderRepository.findById(id)).isNotEmpty();

        mockMvc.perform(delete("/api/v1/orders/{id}", id))
                .andExpect(status().isNoContent());

        assertThat(orderRepository.findById(id)).isEmpty();
    }

    @Test
    void getOrderById_shouldReturnSavedOrder() throws Exception {
        Order order = initOrder(false);
        long id = order.getId();

        assertThat(orderRepository.findById(id)).isNotEmpty();

        mockMvc.perform(get("/api/v1/orders/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id));
    }

    @Test
    void getAllOrders_shouldReturnAllSavedOrders() throws Exception {
        initOrder(true);
        initOrder(false);

        mockMvc.perform(get("/api/v1/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    private Order initOrder(boolean withPayment) {
        Order order = TestDataFactory.createOrder();
        if (withPayment) {
            order.setPaymentId(1L);
            order.setStatus(OrderStatus.PAYMENT_PROCESSING);
        }
        return orderRepository.save(order);
    }

    private BigDecimal calculateTotalAmount(List<OrderItemDto.Request.Base> items) {
        if (items == null || items.isEmpty()) {
            return BigDecimal.ZERO;
        }
        return items.stream()
                .map(it -> it.price().multiply(BigDecimal.valueOf(it.quantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
