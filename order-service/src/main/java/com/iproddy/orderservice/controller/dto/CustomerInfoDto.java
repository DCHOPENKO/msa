package com.iproddy.orderservice.controller.dto;

public enum CustomerInfoDto {
    ;
    public enum Request{
        ;
        public record Base(
                String customerName,
                String email,
                String phoneNumber
        ) {}
    }
    public enum Response{
        ;
        public record Base(
                String customerName,
                String email,
                String phoneNumber
        ) {}
    }
}
