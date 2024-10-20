package com.dch.saga_pattern.storage.repository;

import com.dch.saga_pattern.model.ProductType;
import com.dch.saga_pattern.storage.entity.ProductEntity;
import com.dch.saga_pattern.util.ProductTestDataUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


@DataJpaTest
@ExtendWith(SpringExtension.class)
class ProductRepositoryTest {
    public static final ProductEntity PRODUCT_A = ProductTestDataUtil.createProduct("Test", BigDecimal.TEN, ProductType.A);
    @Autowired
    private ProductRepository productRepository;

    @AfterEach
    void cleanUp() {
        productRepository.deleteAll();
    }

    @Test
    void findByProductId() {
        // given
        var saved = productRepository.save(PRODUCT_A);
        // when
        var found = productRepository.findByProductId(saved.getProductId());
        // then
        assertNotNull(found);
        assertTrue(found.isPresent());
        var product = found.get();

        assertEquals(saved.getId(), product.getId());
        assertEquals(saved.getProductId(), product.getProductId());
        assertEquals(saved.getName(), product.getName());
        assertEquals(saved.getPrice(), product.getPrice());
        assertEquals(saved.getType(), product.getType());
    }

    @Test
    void findByProductIdIn() {
        // given
        var saved = productRepository.save(PRODUCT_A);
        // when
        var found = productRepository.findByProductIdIn(Set.of(saved.getProductId()));
        // then
        assertNotNull(found);
        assertEquals(1, found.size());

        var product = found.iterator().next();
        assertEquals(saved.getId(), product.getId());
        assertEquals(saved.getProductId(), product.getProductId());
        assertEquals(saved.getName(), product.getName());
        assertEquals(saved.getPrice(), product.getPrice());
        assertEquals(saved.getType(), product.getType());
    }

    @Test
    void testSave() {
        // given
        // when
        var saved = productRepository.save(PRODUCT_A);
        // then
        assertNotNull(saved);

        assertEquals(PRODUCT_A.getName(), saved.getName());
        assertEquals(PRODUCT_A.getPrice(), saved.getPrice());
        assertEquals(PRODUCT_A.getType(), saved.getType());
        assertEquals(PRODUCT_A.getProductId(), saved.getProductId());

        assertNotNull(saved.getProductId());
        assertNotNull(saved.getId());
        assertNotNull(saved.getCreatedAt());
        assertNotNull(saved.getUpdatedAt());
        assertNotNull(saved.getVersion());
    }
}