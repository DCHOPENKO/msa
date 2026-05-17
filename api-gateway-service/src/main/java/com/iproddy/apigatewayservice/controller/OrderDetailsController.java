package com.iproddy.apigatewayservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/order-details")
@RequiredArgsConstructor
public class OrderDetailsController {

    private final WebClient webClient;

    @GetMapping("/{orderId}")
    public Mono<Map<String, Object>> getOrderDetails(@PathVariable Long orderId) {
        // Асинхронный запрос для получения данных о заказе
        Mono<String> orderResult = webClient.get()
            .uri("http://localhost:8081/api/v1/orders/" + orderId)
            .retrieve()
            .bodyToMono(String.class);

        // Асинхронный запрос для получения данных об оплате
        Mono<String> paymentResult = webClient.get()
            .uri("http://localhost:8082/api/v1/payments/by-order/" + orderId)
            .retrieve()
            .bodyToMono(String.class);

        // Асинхронный запрос для получения данных о доставке
        Mono<String> deliveryResult = webClient.get()
            .uri("http://localhost:8083/api/v1/deliveries/by-order/" + orderId)
            .retrieve()
            .bodyToMono(String.class);

        return Mono.zip(orderResult, paymentResult, deliveryResult)
            .map(tuple -> {
                Map<String, Object> result = new HashMap<>();
                result.put("order", tuple.getT1());
                result.put("payment", tuple.getT2());
                result.put("delivery", tuple.getT3());
                return result;
            });
    }
}
