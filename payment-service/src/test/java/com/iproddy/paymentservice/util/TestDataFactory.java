package com.iproddy.paymentservice.util;

import com.iproddy.common.dto.http.PaymentDto;
import com.iproddy.paymentservice.model.entity.Payment;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.Random;

public final class TestDataFactory {

    private static final PodamFactory podam = new PodamFactoryImpl();
    private static final Random random = new Random();

    public static Payment createPayment() {
        Payment payment = podam.manufacturePojo(Payment.class);
        payment.setId(null);
        return payment;
    }

    public static PaymentDto.Request.Base createPaymentBaseRequest() {
        return podam.manufacturePojo(PaymentDto.Request.Base.class);
    }
}