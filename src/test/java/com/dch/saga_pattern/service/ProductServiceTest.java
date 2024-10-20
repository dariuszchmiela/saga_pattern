package com.dch.saga_pattern.service;

import com.dch.saga_pattern.exception.ProductAlreadyExistException;
import com.dch.saga_pattern.model.Product;
import com.dch.saga_pattern.model.ProductDto;
import com.dch.saga_pattern.model.ProductType;
import com.dch.saga_pattern.storage.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
class ProductServiceTest {
    private static final String PRODUCT_A = "Product A";
    private static final ProductDto CREATE_PRODUCT_DTO = new ProductDto(PRODUCT_A, BigDecimal.ONE, ProductType.A);
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
        Product result = productService.createProduct(CREATE_PRODUCT_DTO);
        // then

        assertThat(productRepository.findByProductId(result.getProductId()))
                .isPresent()
                .get()
                .satisfies(product -> {
                    assertThat(product.getName()).isEqualTo(PRODUCT_A);
                    assertThat(product.getProductId()).isEqualTo(result.getProductId());
                    assertThat(product.getId()).isNotNull();
                    assertThat(product.getPrice().compareTo(BigDecimal.ONE)).isZero();
                    assertThat(product.getType()).isEqualTo(ProductType.A);
                });
    }

    @Test
    void createProductAlreadyExists() {
        // given
        productService.createProduct(CREATE_PRODUCT_DTO);
        // when & then
        assertThatThrownBy(() -> productService.createProduct(CREATE_PRODUCT_DTO))
                .isInstanceOf(ProductAlreadyExistException.class)
                .hasMessageContaining("Product with productId ");
    }
}