package com.dch.saga_pattern.model;

import java.util.UUID;

public class Payment {
    private Long id;
    private UUID paymentId;
    private PaymentStatus status;

    public Payment() {
    }

    public Payment(Long id, UUID paymentId, PaymentStatus status) {
        this.id = id;
        this.paymentId = paymentId;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public Payment setId(Long id) {
        this.id = id;
        return this;
    }

    public UUID getPaymentId() {
        return paymentId;
    }

    public Payment setPaymentId(UUID paymentId) {
        this.paymentId = paymentId;
        return this;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public Payment setStatus(PaymentStatus status) {
        this.status = status;
        return this;
    }
}