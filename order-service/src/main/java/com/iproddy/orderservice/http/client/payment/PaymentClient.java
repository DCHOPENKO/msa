package com.iproddy.orderservice.http.client.payment;

import com.iproddy.orderservice.http.client.payment.dto.PaymentDto;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import tools.jackson.databind.json.JsonMapper;

import java.nio.ByteBuffer;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class PaymentClient {

    private final PaymentFeignClient paymentFeignClient;
    private final JsonMapper mapper;

    public PaymentDto.Response.Base createPayment(PaymentDto.Request.Base request) {
        try {
            return paymentFeignClient.createPayment(request.orderId(), request);
        } catch (FeignException ex) {
            return processException(ex);
        }
    }

    private PaymentDto.Response.Base processException(FeignException ex) {
        HttpStatusCode statusCode = HttpStatusCode.valueOf(ex.status());
        Optional<ByteBuffer> bodyOptional = ex.responseBody();

        if (isAcceptable(statusCode) && bodyOptional.isPresent()) {
            return getResponse(bodyOptional.get());
        } else {
            throw new RuntimeException("Не удалось забронировать время");
        }
    }

    private boolean isAcceptable(HttpStatusCode statusCode) {
        return statusCode.is2xxSuccessful() || statusCode.isSameCodeAs(HttpStatus.CONFLICT);
    }

    private PaymentDto.Response.Base getResponse(ByteBuffer body) {
        return mapper.readValue(body.array(), PaymentDto.Response.Base.class);
    }
}
