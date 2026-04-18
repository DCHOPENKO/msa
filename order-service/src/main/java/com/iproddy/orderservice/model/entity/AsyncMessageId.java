package com.iproddy.orderservice.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AsyncMessageId implements Serializable {

    private UUID id;

    private String topic;
}
