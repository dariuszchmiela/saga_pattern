package com.dch.saga_pattern.storage.repository;

import com.dch.saga_pattern.model.PaymentStatus;
import com.dch.saga_pattern.model.ProductType;
import com.dch.saga_pattern.util.OrderTestDataUtil;
import com.dch.saga_pattern.util.PaymentTestDataUtil;
import com.dch.saga_pattern.util.ProductTestDataUtil;
import com.dch.saga_pattern.util.UserTestDataUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@ExtendWith(SpringExtension.class)
class PaymentRepositoryTest {
    public static final String EMAIL = "daro.programmer@fakemail.com";

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
        productRepository.deleteAll();
    }

    @Test
    void testSave() {
        // given
        var user = userRepository.save(UserTestDataUtil.createTestUser(EMAIL));
        var product = productRepository.save(ProductTestDataUtil.createProduct("Test", BigDecimal.TEN, ProductType.A));
        var order = orderRepository.save(OrderTestDataUtil.createTestOrder(user, product));
        // when
        var savedPayment = paymentRepository.save(PaymentTestDataUtil.createTestPayment(BigDecimal.TEN, PaymentStatus.PENDING, order));
        // then
        assertNotNull(savedPayment.getId());
        assertNotNull(savedPayment.getPaymentId());
        assertNotNull(savedPayment.getAmount());
        assertNotNull(savedPayment.getStatus());
        assertNotNull(savedPayment.getCreatedAt());
        assertNotNull(savedPayment.getUpdatedAt());
        assertNotNull(savedPayment.getVersion());

        assertNotNull(savedPayment.getOrder());
        assertEquals(order.getOrderId(), savedPayment.getOrder().getOrderId());
    }
}