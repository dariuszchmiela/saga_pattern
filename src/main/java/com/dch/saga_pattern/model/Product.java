package com.dch.saga_pattern.model;

import java.math.BigDecimal;
import java.util.UUID;

public class Product {
    private Long id;
    private UUID productId;
    private String name;
    private BigDecimal price;
    private ProductType productType;

    public Product(Long id, UUID productId, String name, BigDecimal price, ProductType productType) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.productType = productType;
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

    public ProductType getProductType() {
        return productType;
    }

    public Product setProductType(ProductType productType) {
        this.productType = productType;
        return this;
    }
}
