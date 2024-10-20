package com.dch.saga_pattern.exception;

public class PaymentNotExistException extends RuntimeException {
    public PaymentNotExistException(String message) {
        super(message);
    }
}
