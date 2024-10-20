package com.dch.saga_pattern.util;

import com.dch.saga_pattern.model.PaymentStatus;
import com.dch.saga_pattern.storage.entity.OrderEntity;
import com.dch.saga_pattern.storage.entity.PaymentEntity;

import java.math.BigDecimal;
import java.util.UUID;

public class PaymentTestDataUtil {

    public static PaymentEntity createTestPayment(BigDecimal amount, PaymentStatus status) {
        return new PaymentEntity(UUID.randomUUID(), amount, status);
    }

    public static PaymentEntity createTestPayment(BigDecimal amount, PaymentStatus status, OrderEntity order) {
        return new PaymentEntity(UUID.randomUUID(), amount, status, order);
    }

    private PaymentTestDataUtil() {
        throw new IllegalStateException("Test data util class");
    }
}
