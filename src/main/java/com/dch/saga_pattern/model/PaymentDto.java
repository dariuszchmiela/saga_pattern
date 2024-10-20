package com.dch.saga_pattern.model;

import java.math.BigDecimal;
import java.util.UUID;

public class PaymentDto {
    private UUID paymentId;
    private BigDecimal amount;
    private PaymentStatus paymentStatus;
    private UUID orderId;

    public PaymentDto() {
    }

    public PaymentDto(BigDecimal amount, PaymentStatus paymentStatus) {
        this.paymentId = UUID.randomUUID();
        this.amount = amount;
        this.paymentStatus = paymentStatus;
    }

    public PaymentDto(BigDecimal amount, PaymentStatus paymentStatus, UUID orderId) {
        this.paymentId = UUID.randomUUID();
        this.amount = amount;
        this.paymentStatus = paymentStatus;
        this.orderId = orderId;
    }

    public UUID getPaymentId() {
        return paymentId;
    }

    public PaymentDto setPaymentId(UUID paymentId) {
        this.paymentId = paymentId;
        return this;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public PaymentDto setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentMethod) {
        this.paymentStatus = paymentMethod;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public PaymentDto setOrderId(UUID orderId) {
        this.orderId = orderId;
        return this;
    }

    @Override
    public String toString() {
        return "PaymentDto{" +
                "paymentId=" + paymentId +
                ", amount=" + amount +
                ", paymentStatus=" + paymentStatus +
                ", orderId=" + orderId +
                '}';
    }
}