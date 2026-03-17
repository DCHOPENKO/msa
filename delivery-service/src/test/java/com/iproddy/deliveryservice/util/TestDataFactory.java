package com.iproddy.deliveryservice.util;

import com.iproddy.common.dto.http.DeliveryDto;
import com.iproddy.deliveryservice.model.entity.Delivery;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.Random;

public final class TestDataFactory {

    private static final PodamFactory podam = new PodamFactoryImpl();
    private static final Random random = new Random();

    public static Delivery createDelivery() {
        Delivery delivery = podam.manufacturePojo(Delivery.class);
        delivery.setId(null);
        return delivery;
    }

    public static DeliveryDto.Request.Base createDeliveryBaseRequest() {
        return podam.manufacturePojo(DeliveryDto.Request.Base.class);
    }
}