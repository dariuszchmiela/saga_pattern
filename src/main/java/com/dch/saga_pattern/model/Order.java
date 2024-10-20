package com.dch.saga_pattern.model;

import java.util.Set;
import java.util.UUID;

public class Order {
    private Long id;
    private UUID orderId;
    private Set<Product> products;
    private UserDto user;

    public Order() {
    }

    public Order(Long id, UUID orderId, Set<Product> products, UserDto user) {
        this.id = id;
        this.orderId = orderId;
        this.products = products;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public Order setId(Long id) {
        this.id = id;
        return this;
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

    public UserDto getUser() {
        return user;
    }

    public Order setUser(UserDto user) {
        this.user = user;
        return this;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", products=" + products +
                ", user=" + user +
                '}';
    }
}