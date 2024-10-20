package com.dch.saga_pattern.service;

import com.dch.saga_pattern.model.OrderDto;
import com.dch.saga_pattern.model.ProductType;
import com.dch.saga_pattern.storage.entity.OrderEntity;
import com.dch.saga_pattern.storage.entity.ProductEntity;
import com.dch.saga_pattern.storage.entity.UserEntity;
import com.dch.saga_pattern.storage.repository.OrderRepository;
import com.dch.saga_pattern.storage.repository.ProductRepository;
import com.dch.saga_pattern.storage.repository.UserRepository;
import com.dch.saga_pattern.util.ProductTestDataUtil;
import com.dch.saga_pattern.util.UserTestDataUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class CreateOrderSagaStepTest {
    private static final String EMAIL = "derek.programmer@fakemail.com";
    private static final UserEntity USER_ENTITY = UserTestDataUtil.createTestUser(EMAIL);
    private static final ProductEntity PRODUCT_ENTITY = ProductTestDataUtil.createProduct("PRoduct A", BigDecimal.TEN, ProductType.A);
    @Autowired
    private CreateOrderSagaStep createOrderSagaStep;
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
    void execute() {
        // given
        userRepository.save(USER_ENTITY);
        productRepository.save(PRODUCT_ENTITY);
        var createOrderDto = new OrderDto(UUID.randomUUID(), USER_ENTITY.getUserId(), Set.of(PRODUCT_ENTITY.getProductId()));
        // when
        var result = createOrderSagaStep.execute(createOrderDto);
        // then
        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getOrderId());
        assertNotNull(result.getCreatedAt());
        assertNotNull(result.getUpdatedAt());
        assertNotNull(result.getVersion());
    }

    @Test
    void compensate() {
        // when
        userRepository.save(USER_ENTITY);
        productRepository.save(PRODUCT_ENTITY);
        var createOrderDto = new OrderDto(UUID.randomUUID(), USER_ENTITY.getUserId(), Set.of(PRODUCT_ENTITY.getProductId()));
        var orderEntity = createOrderSagaStep.execute(createOrderDto);
        // when
        createOrderSagaStep.compensate(createOrderDto);
        // then
        Optional<OrderEntity> resultOpt = orderRepository.findByOrderId(orderEntity.getOrderId());
        assertTrue(resultOpt.isEmpty());
    }
}