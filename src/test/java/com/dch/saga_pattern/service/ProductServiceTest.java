package com.dch.saga_pattern.service;

import com.dch.saga_pattern.exception.ProductAlreadyExistException;
import com.dch.saga_pattern.model.CreateProductDto;
import com.dch.saga_pattern.model.ProductType;
import com.dch.saga_pattern.storage.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
class ProductServiceTest {
    public static final UUID PRODUCT_ID = UUID.randomUUID();
    public static final String PRODUCT_A = "Product A";
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductService productService;

    @AfterEach
    void cleanUp() {
        productRepository.deleteAll();
    }

    @Test
    void createProduct() {
        // given
        assertTrue(productRepository.findAll().isEmpty());
        // when
        productService.createProduct(new CreateProductDto(PRODUCT_ID, PRODUCT_A, BigDecimal.ONE, ProductType.A));
        // then

        assertThat(productRepository.findByProductId(PRODUCT_ID))
                .isPresent()
                .get()
                .satisfies(product -> {
                    assertThat(product.getName()).isEqualTo(PRODUCT_A);
                    assertThat(product.getProductId()).isEqualTo(PRODUCT_ID);
                    assertThat(product.getId()).isNotNull();
                    assertThat(product.getPrice().compareTo(BigDecimal.ONE)).isZero();
                    assertThat(product.getProductType()).isEqualTo(ProductType.A);
                });
    }

    @Test
    void createProductAlreadyExists() {
        // given
        productService.createProduct(new CreateProductDto(PRODUCT_ID, PRODUCT_A, BigDecimal.ONE, ProductType.A));
        // when & then
        assertThatThrownBy(() -> productService.createProduct(new CreateProductDto(PRODUCT_ID, PRODUCT_A, BigDecimal.ONE, ProductType.A)))
                .isInstanceOf(ProductAlreadyExistException.class)
                .hasMessageContaining("Product with productId " + PRODUCT_ID + " already exists");
    }
}