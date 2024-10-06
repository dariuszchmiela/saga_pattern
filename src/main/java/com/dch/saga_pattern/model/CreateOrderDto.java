package com.dch.saga_pattern.model;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class CreateOrderDto {
    private final UUID orderId;
    private final Set<Product> products;

    public CreateOrderDto(UUID orderId, Set<Product> products) {
        this.orderId = orderId;
        this.products = products;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public Set<UUID> getProductIds() {
        if (products == null) {
            return Set.of();
        }
        return products.stream().map(Product::getProductId).collect(Collectors.toSet());
    }
}
