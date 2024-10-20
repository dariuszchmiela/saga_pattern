package com.dch.saga_pattern.model;

import java.math.BigDecimal;
import java.util.UUID;

public class ProductDto {
    private UUID productId;
    private String name;
    private BigDecimal price;
    private ProductType type;

    public ProductDto() {
    }

    public ProductDto(String name, BigDecimal price, ProductType type) {
        this.productId = UUID.randomUUID();
        this.name = name;
        this.price = price;
        this.type = type;
    }

    public UUID getProductId() {
        return productId;
    }

    public ProductDto setProductId(UUID productId) {
        this.productId = productId;
        return this;
    }

    public String getName() {
        return name;
    }

    public ProductDto setName(String name) {
        this.name = name;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ProductDto setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public ProductType getType() {
        return type;
    }

    public ProductDto setType(ProductType type) {
        this.type = type;
        return this;
    }

    @Override
    public String toString() {
        return "ProductDto{" +
                "productId=" + productId +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", type=" + type +
                '}';
    }
}
