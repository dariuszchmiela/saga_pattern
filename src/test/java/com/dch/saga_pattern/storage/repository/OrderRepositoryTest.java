package com.dch.saga_pattern.storage.repository;

import com.dch.saga_pattern.model.ProductType;
import com.dch.saga_pattern.storage.entity.OrderEntity;
import com.dch.saga_pattern.storage.entity.ProductEntity;
import com.dch.saga_pattern.storage.entity.UserEntity;
import com.dch.saga_pattern.util.ProductTestDataUtil;
import com.dch.saga_pattern.util.UserTestDataUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ExtendWith(SpringExtension.class)
class OrderRepositoryTest {
    public static final String EMAIL = "miro.programmer@fakemail.com";
    public static final UserEntity TEST_USER = UserTestDataUtil.createTestUser(EMAIL);
    public static final ProductEntity PRODUCT_A = ProductTestDataUtil.createProduct("Test", BigDecimal.TEN, ProductType.A);

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;

    @AfterEach
    void cleanUp() {
        orderRepository.deleteAll();
        userRepository.deleteAll();
        productRepository.deleteAll();
    }

    @Test
    void findByOrderId() {
        // given
        var user = userRepository.save(TEST_USER);
        var product = productRepository.save(PRODUCT_A);
        var order = new OrderEntity(UUID.randomUUID(), user, Set.of(product));
        var savedOrder = orderRepository.save(order);
        // when
        var found = orderRepository.findByOrderId(savedOrder.getOrderId());
        // then
        assertNotNull(found);
        assertTrue(found.isPresent());
        assertEquals(savedOrder.getId(), found.get().getId());
        assertEquals(savedOrder.getOrderId(), found.get().getOrderId());
    }

    @Test
    void deleteByOrderId() {
        // given
        var user = userRepository.save(TEST_USER);
        var product = productRepository.save(PRODUCT_A);
        var order = new OrderEntity(UUID.randomUUID(), user, Set.of(product));
        var savedOrder = orderRepository.save(order);
        // when
        orderRepository.deleteByOrderId(savedOrder.getOrderId());
        // then
        assertTrue(orderRepository.findByOrderId(savedOrder.getOrderId()).isEmpty());
    }

    @Test
    void testSave() {
        // given & when
        var user = userRepository.save(TEST_USER);
        var product = productRepository.save(PRODUCT_A);
        var order = new OrderEntity(UUID.randomUUID(), user, Set.of(product));
        var savedOrder = orderRepository.save(order);
        // then
        assertNotNull(savedOrder.getId());
        assertTrue(savedOrder.getProducts().contains(product));
        assertEquals(user.getId(), savedOrder.getUser().getId());
        assertNotNull(savedOrder.getCreatedAt());
        assertNotNull(savedOrder.getUpdatedAt());
        assertNotNull(savedOrder.getVersion());
    }
}