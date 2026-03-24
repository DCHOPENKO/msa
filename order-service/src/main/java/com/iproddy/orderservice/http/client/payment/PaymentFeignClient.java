package com.iproddy.orderservice.http.client.payment;

import com.iproddy.common.util.constant.WebConstants;
import com.iproddy.orderservice.http.client.payment.dto.PaymentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "payment-client", contextId = "payment-client")
public interface PaymentFeignClient {

    @PostMapping("/api/v1/payments")
    PaymentDto.Response.Base createPayment(@RequestHeader(WebConstants.IDEMPOTENT_KEY_HEADER_NAME) Long idempotencyKey,
                                           @RequestBody PaymentDto.Request.Base request);

}
