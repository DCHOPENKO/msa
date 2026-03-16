package com.iproddy.common.exception;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record ErrorResponse(
        List<String> messages,
        String timestamp
) {
    public static ErrorResponse of(List<String> messages) {
        return new ErrorResponse(messages, LocalDateTime.now().toString());
    }
}