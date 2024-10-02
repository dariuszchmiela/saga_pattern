package com.dch.saga_pattern.model;

public class Payment {
    private String paymentId;
    private String orderId;
    private String status;  // pending, completed, failed

    public Payment() {
    }

    public Payment(String paymentId, String orderId, String status) {
        this.paymentId = paymentId;
        this.orderId = orderId;
        this.status = status;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "paymentId='" + paymentId + '\'' +
                ", orderId='" + orderId + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}