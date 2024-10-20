package com.dch.saga_pattern.controller;

import com.dch.saga_pattern.model.PaymentDto;
import com.dch.saga_pattern.model.PaymentStatus;
import com.dch.saga_pattern.model.ProductType;
import com.dch.saga_pattern.storage.entity.OrderEntity;
import com.dch.saga_pattern.storage.entity.PaymentEntity;
import com.dch.saga_pattern.storage.entity.ProductEntity;
import com.dch.saga_pattern.storage.entity.UserEntity;
import com.dch.saga_pattern.storage.repository.OrderRepository;
import com.dch.saga_pattern.storage.repository.PaymentRepository;
import com.dch.saga_pattern.storage.repository.ProductRepository;
import com.dch.saga_pattern.storage.repository.UserRepository;
import com.dch.saga_pattern.util.OrderTestDataUtil;
import com.dch.saga_pattern.util.PaymentTestDataUtil;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PaymentControllerTest {
    private static final String EMAIL = "radzio.programmer@fakemail.com";
    private static final String BASE_PATH = "/api/payments";
    private static final BigDecimal AMOUNT = BigDecimal.TEN;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;

    @AfterEach
    void cleanUp() {
        paymentRepository.deleteAll();
        orderRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void createPayment() throws Exception {
        // given
        UserEntity user = userRepository.save(UserTestDataUtil.createTestUser(EMAIL));
        ProductEntity product = productRepository.save(ProductTestDataUtil.createProduct("Product A", BigDecimal.TEN, ProductType.A));
        OrderEntity orderEntity = orderRepository.save(OrderTestDataUtil.createTestOrder(user, product));
        final PaymentDto paymentDto = new PaymentDto(AMOUNT, PaymentStatus.PENDING, orderEntity.getOrderId());
        // when & then
        mvc.perform(post(BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(paymentDto)))
                .andExpect(status().isCreated());
    }

    @Test
    void deletePayment() throws Exception {
        // given
        PaymentEntity saved = paymentRepository.save(PaymentTestDataUtil.createTestPayment(BigDecimal.TEN, PaymentStatus.PENDING));
        // when & then
        mvc.perform(delete(BASE_PATH + "/{paymentId}", saved.getPaymentId()))
                .andExpect(status().isNoContent());
    }
}