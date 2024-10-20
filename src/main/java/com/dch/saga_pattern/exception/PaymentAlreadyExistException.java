package com.dch.saga_pattern.exception;

public class PaymentAlreadyExistException extends RuntimeException {
    public PaymentAlreadyExistException(String message) {
        super(message);
    }
}