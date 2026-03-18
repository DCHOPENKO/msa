package com.iproddy.deliveryservice.controller.dto;

public enum ShippingAddressDto {
    ;
    public enum  Request{
        ;
        public record Base(
                String city,
                String street,
                String house,

                String apartment
        ) {}
    }
    public enum Response{
        ;
        public record Base(
                String city,
                String street,
                String house,
                String apartment
        ) {}
    }
}
