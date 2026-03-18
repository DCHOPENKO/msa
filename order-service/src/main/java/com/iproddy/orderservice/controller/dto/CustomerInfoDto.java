package com.iproddy.orderservice.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public enum CustomerInfoDto {
    ;
    public enum Request{
        ;
        @Schema(name = "CustomerInfoBaseRequest", description = "Customer base information in order request")
        public record Base(
                @Schema(description = "Customer full name", example = "Ivan Ivanov")
                String customerName,
                @Schema(description = "Customer email", example = "ivan-ivanov@gmail.com")
                String email,
                @Schema(description = "Customer contact phone number", example = "79801112233")
                String phoneNumber
        ) {}
    }
    public enum Response{
        ;
        @Schema(name = "CustomerInfoBaseResponse", description = "Customer base information in order response")
        public record Base(
                @Schema(description = "Customer full name", example = "Ivan Ivanov")
                String customerName,
                @Schema(description = "Customer email", example = "ivan-ivanov@gmail.com")
                String email,
                @Schema(description = "Customer contact phone number", example = "79801112233")
                String phoneNumber
        ) {}
    }
}
