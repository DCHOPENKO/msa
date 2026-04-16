package com.iproddy.common.exception;

public class TransactionOutboxSendingException extends RuntimeException {

    public TransactionOutboxSendingException(String message,
    Throwable cause) {
        super(message, cause);
    }
}
