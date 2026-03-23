package com.iproddy.common.exception;

public class IdempotencyKeyExistsException extends RuntimeException {

    public IdempotencyKeyExistsException(String message,
                                         Throwable cause) {
        super(message, cause);
    }
}
