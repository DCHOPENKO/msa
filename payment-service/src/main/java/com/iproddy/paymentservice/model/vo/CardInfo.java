package com.iproddy.paymentservice.model.vo;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class CardInfo {

    private String cardHolder;

    private String cardNumber;

    private Integer expirationMonth;

    private Integer expirationYear;
}