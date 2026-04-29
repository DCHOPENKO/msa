package com.iproddy.orderhistoryservice.model.dto;

import lombok.Data;

@Data
public class ShippingAddress {

    private String city;
    private String street;
    private String house;
    private String apartment;
}
