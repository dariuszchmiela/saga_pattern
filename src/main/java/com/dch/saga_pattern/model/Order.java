package com.dch.saga_pattern.model;

import java.util.Set;
import java.util.UUID;

public class Order {
    private Long id;
    private UUID orderId;
    private Set<Product> products;

    public Order() {
    }

    public Order(Long id, UUID orderId, Set<Product> products) {
        this.id = id;
        this.orderId = orderId;
        this.products = products;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public Order setOrderId(UUID orderId) {
        this.orderId = orderId;
        return this;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public Order setProducts(Set<Product> products) {
        this.products = products;
        return this;
    }

    public Long getId() {
        return id;
    }

    public Order setId(Long id) {
        this.id = id;
        return this;
    }
}