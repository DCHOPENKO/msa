package com.iproddy.common.model.vo;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class ShippingAddress {

    private String city;
    private String street;
    private String house;
    private String apartment;
}
