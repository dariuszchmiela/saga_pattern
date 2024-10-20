package com.dch.saga_pattern.service;

import com.dch.saga_pattern.exception.OrderAlreadyExistException;
import com.dch.saga_pattern.model.OrderDto;
import com.dch.saga_pattern.model.ProductType;
import com.dch.saga_pattern.storage.repository.OrderRepository;
import com.dch.saga_pattern.storage.repository.ProductRepository;
import com.dch.saga_pattern.storage.repository.UserRepository;
import com.dch.saga_pattern.util.ProductTestDataUtil;
import com.dch.saga_pattern.util.UserTestDataUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class OrderServiceTest {
    private static final String EMAIL = "robert.programmer@fakemail.com";

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderService orderService;

    @AfterEach
    void cleanUp() {
        orderRepository.deleteAll();
        productRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @Transactional
    void createOrder() {
        // given
        assertTrue(orderRepository.findAll().isEmpty());
        var product = productRepository.save(ProductTestDataUtil.createProduct("Product A", BigDecimal.ONE, ProductType.A));
        var user = userRepository.save(UserTestDataUtil.createTestUser(EMAIL));
        // when
        UUID orderId = UUID.randomUUID();
        orderService.createOrder(new OrderDto(orderId, user.getUserId(), Set.of(product.getProductId())));
        // then
        assertThat(orderRepository.findByOrderId(orderId))
                .isPresent()
                .get()
                .satisfies(order -> {
                    assertThat(order.getOrderId()).isEqualTo(orderId);
                    assertThat(order.getUser().getId()).isEqualTo(user.getId());
                    assertThat(order.getProducts()).isNotEmpty();
                    assertThat(order.getProducts()).hasSize(1);
                    assertThat(order.getProducts().iterator().next().getProductId()).isEqualTo(product.getProductId());
                    assertThat(order.getProducts().iterator().next().getName()).isEqualTo(product.getName());
                    assertThat(order.getProducts().iterator().next().getType()).isEqualTo(ProductType.A);
                    assertThat(order.getProducts().iterator().next().getPrice()).isEqualTo(BigDecimal.ONE);
                });
    }

    @Test
    @Transactional
    void createOrderAlreadyExists() {
        // given
        assertTrue(orderRepository.findAll().isEmpty());
        var product = productRepository.save(ProductTestDataUtil.createProduct("Product A", BigDecimal.ONE, ProductType.A));
        var user = userRepository.save(UserTestDataUtil.createTestUser(EMAIL));
        UUID orderId = UUID.randomUUID();
        orderService.createOrder(new OrderDto(orderId, user.getUserId(), Set.of(product.getProductId())));

        // when
        // then
        assertThatThrownBy(() -> orderService.createOrder(new OrderDto(orderId, user.getUserId(), Set.of(product.getProductId()))))
                .isInstanceOf(OrderAlreadyExistException.class)
                .hasMessage("Order with orderId " + orderId + " already exists");
    }
}