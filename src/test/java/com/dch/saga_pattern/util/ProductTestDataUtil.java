package com.dch.saga_pattern.util;

import com.dch.saga_pattern.model.ProductType;
import com.dch.saga_pattern.storage.entity.ProductEntity;

import java.math.BigDecimal;
import java.util.UUID;

public class ProductTestDataUtil {
    private static final String PRODUCT_NAME_A = "Product A";
    private static final BigDecimal price = BigDecimal.valueOf(100);
    private static final ProductType type = ProductType.A;

    public static ProductEntity createProduct(String name, BigDecimal price, ProductType type) {
        ProductEntity product = new ProductEntity();
        product.setProductId(UUID.randomUUID());
        product.setName(name);
        product.setPrice(price);
        product.setType(type);
        return product;
    }

    public static ProductEntity createProductA() {
        return createProduct(PRODUCT_NAME_A, price, type);
    }

    private ProductTestDataUtil() {
        throw new IllegalStateException("Test data util class");
    }
}
