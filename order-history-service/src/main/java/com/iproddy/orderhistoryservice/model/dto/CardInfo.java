package com.iproddy.orderhistoryservice.model.dto;

import lombok.Data;

@Data
public class CardInfo {

    private String cardHolder;
    private String cardNumber;
    private Integer expirationMonth;
    private Integer expirationYear;
}