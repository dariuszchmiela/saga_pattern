package com.dch.saga_pattern.model;

import java.math.BigDecimal;
import java.util.UUID;

public class Product {
    private Long id;
    private UUID productId;
    private String name;
    private BigDecimal price;
    private ProductType type;

    public Product(Long id, UUID productId, String name, BigDecimal price, ProductType type) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public Product setId(Long id) {
        this.id = id;
        return this;
    }

    public UUID getProductId() {
        return productId;
    }

    public Product setProductId(UUID productId) {
        this.productId = productId;
        return this;
    }

    public String getName() {
        return name;
    }

    public Product setName(String name) {
        this.name = name;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Product setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public ProductType getType() {
        return type;
    }

    public Product setType(ProductType type) {
        this.type = type;
        return this;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productId=" + productId +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", type=" + type +
                '}';
    }
}
