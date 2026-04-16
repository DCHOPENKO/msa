package com.iproddy.orderservice.util;

import com.iproddy.orderservice.controller.dto.CustomerInfoDto;
import com.iproddy.orderservice.controller.dto.OrderDto;
import com.iproddy.orderservice.controller.dto.OrderItemDto;
import com.iproddy.orderservice.controller.dto.ShippingAddressDto;
import com.iproddy.orderservice.http.client.payment.dto.PaymentDto;
import com.iproddy.orderservice.model.enums.PaymentMethod;
import com.iproddy.orderservice.model.enums.PaymentStatus;
import com.iproddy.orderservice.model.entity.Order;
import com.iproddy.orderservice.model.enums.OrderStatus;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

public final class TestDataFactory {

    private static final PodamFactory podam = new PodamFactoryImpl();
    private static final Random random = new Random();

    public static Order createOrder() {
        Order order = podam.manufacturePojo(Order.class);
        order.setId(null);
        order.setPaymentId(null);
        order.setStatus(OrderStatus.CREATED);
        order.getItems().forEach(it -> {
            it.setId(null);
            it.setOrder(order);
        });
        return order;
    }

    public static OrderDto.Request.Update createOrderUpdateRequest() {

        CustomerInfoDto.Request.Base customer =
                podam.manufacturePojo(CustomerInfoDto.Request.Base.class);

        ShippingAddressDto.Request.Base address =
                podam.manufacturePojo(ShippingAddressDto.Request.Base.class);

        List<OrderItemDto.Request.Base> items = createItems(3);

        return new OrderDto.Request.Update(
                customer,
                address,
                items,
                OrderStatus.CREATED
        );
    }

    public static OrderDto.Request.Create createOrderCreateRequest() {
        CustomerInfoDto.Request.Base customer =
                podam.manufacturePojo(CustomerInfoDto.Request.Base.class);

        ShippingAddressDto.Request.Base address =
                podam.manufacturePojo(ShippingAddressDto.Request.Base.class);

        List<OrderItemDto.Request.Base> items = createItems(3);

        OrderDto.Request.Create.CardInfo cardInfo =
                podam.manufacturePojo(OrderDto.Request.Create.CardInfo.class);

        return new OrderDto.Request.Create(
                customer,
                address,
                items,
                cardInfo,
                PaymentMethod.CARD,
                OrderStatus.CREATED
        );
    }

    public static PaymentDto.Request.Base createPaymentRequest(Long orderId) {
        return new PaymentDto.Request.Base(
                orderId,
                BigDecimal.valueOf(42),
                PaymentMethod.CASH,
                PaymentStatus.CREATED,
                null
        );
    }

    private static List<OrderItemDto.Request.Base> createItems(int count) {
        return java.util.stream.IntStream.range(0, count)
                .mapToObj(i -> createItem())
                .toList();
    }

    private static OrderItemDto.Request.Base createItem() {

        OrderItemDto.Request.Base base =
                podam.manufacturePojo(OrderItemDto.Request.Base.class);

        int quantity = random.nextInt(5) + 1;

        BigDecimal price = BigDecimal.valueOf(random.nextInt(9000) + 1000);

        return new OrderItemDto.Request.Base(
                base.productName(),
                quantity,
                price
        );
    }
}