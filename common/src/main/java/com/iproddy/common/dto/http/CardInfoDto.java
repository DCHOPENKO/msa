package com.iproddy.common.dto.http;

public enum CardInfoDto {
    ;

    public enum Request {
        ;
        public record Base(
                String cardHolder,
                String cardNumber,
                Integer expirationMonth,
                Integer expirationYear
        ) {
        }
    }

    public enum Response {
        ;
        public record Base(
                String cardHolder,
                String cardNumber,
                Integer expirationMonth,
                Integer expirationYear
        ) {
        }
    }
}