package com.dch.saga_pattern.model;

import java.util.Set;
import java.util.UUID;

public class OrderDto {
    private UUID orderId;
    private UUID userId;
    private Set<UUID> productIds;

    public OrderDto() {
    }

    public OrderDto(UUID orderId, UUID userId, Set<UUID> productIds) {
        this.orderId = orderId;
        this.userId = userId;
        this.productIds = productIds;
    }

    public OrderDto(UUID userId, Set<UUID> productIds) {
        this.orderId = UUID.randomUUID();
        this.userId = userId;
        this.productIds = productIds;
    }

    public OrderDto(UUID userId, UUID productId) {
        this.orderId = UUID.randomUUID();
        this.userId = userId;
        this.productIds = Set.of(productId);
    }

    public UUID getOrderId() {
        return orderId;
    }

    public OrderDto setOrderId(UUID orderId) {
        this.orderId = orderId;
        return this;
    }

    public UUID getUserId() {
        return userId;
    }

    public OrderDto setUserId(UUID userId) {
        this.userId = userId;
        return this;
    }

    public Set<UUID> getProductIds() {
        return productIds;
    }

    public OrderDto setProductIds(Set<UUID> productIds) {
        this.productIds = productIds;
        return this;
    }

    @Override
    public String toString() {
        return "OrderDto{" +
                "orderId=" + orderId +
                ", userId=" + userId +
                ", productIds=" + productIds +
                '}';
    }
}