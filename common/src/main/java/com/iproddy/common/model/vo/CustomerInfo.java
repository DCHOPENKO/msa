package com.iproddy.common.model.vo;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class CustomerInfo {

    private String customerName;
    private String email;
    private String phoneNumber;
}
