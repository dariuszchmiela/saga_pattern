package com.dch.saga_pattern.model;

import java.math.BigDecimal;
import java.util.UUID;

public class CreateProductDto {
    private final UUID productId;
    private final String name;
    private final BigDecimal price;
    private final ProductType productType;

    public CreateProductDto(UUID productId, String name, BigDecimal price, ProductType productType) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.productType = productType;
    }

    public UUID getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ProductType getProductType() {
        return productType;
    }
}
