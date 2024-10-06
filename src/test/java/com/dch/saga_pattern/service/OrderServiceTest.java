package com.dch.saga_pattern.service;

import com.dch.saga_pattern.exception.OrderAlreadyExistException;
import com.dch.saga_pattern.model.CreateOrderDto;
import com.dch.saga_pattern.model.CreateProductDto;
import com.dch.saga_pattern.model.Product;
import com.dch.saga_pattern.model.ProductType;
import com.dch.saga_pattern.storage.repository.OrderRepository;
import com.dch.saga_pattern.storage.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class OrderServiceTest {
    public static final String PRODUCT_A = "Product A";
    public static final UUID PRODUCT_ID = UUID.randomUUID();
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderService orderService;

    @AfterEach
    void cleanUp() {
        orderRepository.deleteAll();
        productRepository.deleteAll();
    }

    @Test
    @Transactional
    void createOrder() {
        // given
        assertTrue(orderRepository.findAll().isEmpty());
        Product product = productService.createProduct(new CreateProductDto(PRODUCT_ID, PRODUCT_A, BigDecimal.ONE, ProductType.A));

        // when
        orderService.createOrder(new CreateOrderDto(PRODUCT_ID, Set.of(product)));
        // then
        assertThat(orderRepository.findByOrderId(PRODUCT_ID))
                .isPresent()
                .get()
                .satisfies(order -> {
                    assertThat(order.getOrderId()).isEqualTo(PRODUCT_ID);
                    assertThat(order.getProducts()).isNotEmpty();
                    assertThat(order.getProducts()).hasSize(1);
                    assertThat(order.getProducts().iterator().next().getProductId()).isEqualTo(PRODUCT_ID);
                    assertThat(order.getProducts().iterator().next().getName()).isEqualTo(PRODUCT_A);
                    assertThat(order.getProducts().iterator().next().getProductType()).isEqualTo(ProductType.A);
                    assertThat(order.getProducts().iterator().next().getPrice()).isEqualTo(BigDecimal.ONE);
                });
    }

    @Test
    @Transactional
    void createOrderAlreadyExists() {
        // given
        assertTrue(orderRepository.findAll().isEmpty());
        Product product = productService.createProduct(new CreateProductDto(PRODUCT_ID, PRODUCT_A, BigDecimal.ONE, ProductType.A));
        orderService.createOrder(new CreateOrderDto(PRODUCT_ID, Set.of(product)));

        // when & then
        assertThatThrownBy(() -> orderService.createOrder(new CreateOrderDto(PRODUCT_ID, Set.of(product))))
                .isInstanceOf(OrderAlreadyExistException.class)
                .hasMessageContaining("Order with orderId " + PRODUCT_ID + " already exists");
    }
}