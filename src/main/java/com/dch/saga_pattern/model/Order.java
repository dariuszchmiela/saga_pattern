package com.dch.saga_pattern.model;

public class Order {
    private String orderId;
    private String product;
    private String status;  // pending, completed, cancelled

    public Order() {
    }

    public Order(String orderId, String product, String status) {
        this.orderId = orderId;
        this.product = product;
        this.status = status;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", product='" + product + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}