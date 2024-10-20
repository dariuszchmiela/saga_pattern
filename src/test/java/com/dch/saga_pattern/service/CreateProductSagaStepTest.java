package com.dch.saga_pattern.service;

import com.dch.saga_pattern.model.ProductDto;
import com.dch.saga_pattern.model.ProductType;
import com.dch.saga_pattern.storage.entity.ProductEntity;
import com.dch.saga_pattern.storage.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class CreateProductSagaStepTest {
    private static final ProductDto CREATE_PRODUCT_DTO = new ProductDto(
            "Product A",
            BigDecimal.TEN,
            ProductType.A
    );
    @Autowired
    private CreateProductSagaStep createProductSagaStep;
    @Autowired
    private ProductRepository productRepository;

    @AfterEach
    void cleanUp() {
        productRepository.deleteAll();
    }

    @Test
    void execute() {
        // given
        // when
        ProductEntity result = createProductSagaStep.execute(CREATE_PRODUCT_DTO);
        // then
        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getName());
        assertNotNull(result.getProductId());
        assertNotNull(result.getCreatedAt());
        assertNotNull(result.getUpdatedAt());
        assertNotNull(result.getVersion());
    }

    @Test
    void compensate() {
        // given
        createProductSagaStep.execute(CREATE_PRODUCT_DTO);
        // when & then
        assertDoesNotThrow(() -> createProductSagaStep.compensate(CREATE_PRODUCT_DTO));
    }
}