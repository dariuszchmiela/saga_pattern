package com.dch.saga_pattern.controller;

import com.dch.saga_pattern.model.OrderDto;
import com.dch.saga_pattern.model.ProductType;
import com.dch.saga_pattern.storage.entity.OrderEntity;
import com.dch.saga_pattern.storage.entity.ProductEntity;
import com.dch.saga_pattern.storage.entity.UserEntity;
import com.dch.saga_pattern.storage.repository.OrderRepository;
import com.dch.saga_pattern.storage.repository.PaymentRepository;
import com.dch.saga_pattern.storage.repository.ProductRepository;
import com.dch.saga_pattern.storage.repository.UserRepository;
import com.dch.saga_pattern.util.OrderTestDataUtil;
import com.dch.saga_pattern.util.ProductTestDataUtil;
import com.dch.saga_pattern.util.UserTestDataUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {
    private static final String EMAIL = "piter.programmer@fakemail.com";
    private static final String BASE_PATH = "/api/orders";
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private PaymentRepository paymentRepository;

    @AfterEach
    void cleanUp() {
        orderRepository.deleteAll();
        productRepository.deleteAll();
        paymentRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void createOrder() throws Exception {
        // given
        UserEntity user = userRepository.save(UserTestDataUtil.createTestUser(EMAIL));
        ProductEntity product = productRepository.save(ProductTestDataUtil.createProduct("Product A", BigDecimal.TEN, ProductType.A));
        OrderDto createOrderDto = new OrderDto(user.getUserId(), product.getProductId());
        // when & then
        mvc.perform(post(BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(createOrderDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.orderId").isNotEmpty())
                .andExpect(jsonPath("$.products").isNotEmpty());
    }

    @Test
    void getOrder() throws Exception {
        // given
        UserEntity user = userRepository.save(UserTestDataUtil.createTestUser(EMAIL));
        ProductEntity product = productRepository.save(ProductTestDataUtil.createProduct("Product A", BigDecimal.TEN, ProductType.A));
        OrderEntity saved = orderRepository.save(OrderTestDataUtil.createTestOrder(user, product));
        // when & then
        mvc.perform(get(BASE_PATH + "/{orderId}", saved.getOrderId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").value(saved.getOrderId().toString()))
                .andExpect(jsonPath("$.products").isNotEmpty());
    }

    @Test
    void deleteOrder() throws Exception {
        // given
        UserEntity user = userRepository.save(UserTestDataUtil.createTestUser(EMAIL));
        ProductEntity product = productRepository.save(ProductTestDataUtil.createProduct("Product A", BigDecimal.TEN, ProductType.A));
        OrderEntity saved = orderRepository.save(OrderTestDataUtil.createTestOrder(user, product));
        // when
        mvc.perform(delete(BASE_PATH + "/{orderId}", saved.getOrderId()))
                .andExpect(status().isNoContent());
    }
}