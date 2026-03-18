package com.iproddy.orderservice.http.client.payment;

import com.iproddy.orderservice.http.client.payment.dto.PaymentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "payment-client", contextId = "payment-client")
public interface PaymentFeignClient {

    @PostMapping("/api/v1/payments")
    PaymentDto.Response.Base createPayment(PaymentDto.Request.Base request);

}
