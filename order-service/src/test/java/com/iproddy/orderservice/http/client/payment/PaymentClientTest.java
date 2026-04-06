package com.iproddy.orderservice.http.client.payment;

import com.iproddy.orderservice.IntegrationTestBase;
import com.iproddy.orderservice.http.client.payment.dto.PaymentDto;
import com.iproddy.orderservice.util.TestDataFactory;
import feign.FeignException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.wiremock.spring.ConfigureWireMock;
import org.wiremock.spring.EnableWireMock;

import static org.assertj.core.api.Assertions.assertThat;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;



@EnableWireMock(
        @ConfigureWireMock(
                name = "payment-client",
                port = 9999,
                baseUrlProperties = "spring.cloud.openfeign.client.config.payment-client.url",
                filesUnderClasspath = "wiremock"
        )
)
public class PaymentClientTest extends IntegrationTestBase {

    @Autowired
    private PaymentClient paymentClient;

    @Test
    void createPayment_Success() {
        PaymentDto.Request.Base request = TestDataFactory.createPaymentRequest(200L);

        PaymentDto.Response.Base result = paymentClient.createPayment(request);

        assertThat(request.orderId()).isEqualTo(result.orderId());
        assertThat(request.amount()).isEqualTo(result.amount());
        assertThat(request.method()).isEqualTo(result.method());
        assertThat(request.status()).isEqualTo(result.status());
        verify(postRequestedFor(urlEqualTo("/api/v1/payments")));
    }

    @Test
    void createPayment_Conflict() {
        PaymentDto.Request.Base request = TestDataFactory.createPaymentRequest(409L);

        PaymentDto.Response.Base result = paymentClient.createPayment(request);

        assertThat(request.orderId()).isEqualTo(result.orderId());
        assertThat(request.amount()).isEqualTo(result.amount());
        assertThat(request.method()).isEqualTo(result.method());
        assertThat(request.status()).isEqualTo(result.status());
        verify(postRequestedFor(urlEqualTo("/api/v1/payments")));
    }


    @Test
    void createPayment_ServerError() {
        PaymentDto.Request.Base request = TestDataFactory.createPaymentRequest(500L);

        Assertions.assertThrows(FeignException.InternalServerError.class, () -> paymentClient.createPayment(request));
    }

    @Test
    void createPayment_BadRequest() {
        PaymentDto.Request.Base request = TestDataFactory.createPaymentRequest(400L);

        Assertions.assertThrows(FeignException.BadRequest.class, () -> paymentClient.createPayment(request));
    }

}
