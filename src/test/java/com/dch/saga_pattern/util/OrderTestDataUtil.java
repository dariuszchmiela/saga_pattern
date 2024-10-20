package com.dch.saga_pattern.util;

import com.dch.saga_pattern.storage.entity.OrderEntity;
import com.dch.saga_pattern.storage.entity.ProductEntity;
import com.dch.saga_pattern.storage.entity.UserEntity;

import java.util.Set;
import java.util.UUID;

public class OrderTestDataUtil {

    public static OrderEntity createTestOrder(UserEntity user, ProductEntity... products) {
        return new OrderEntity(UUID.randomUUID(), user, Set.of(products));
    }

    private OrderTestDataUtil() {
        throw new IllegalStateException("Test data util class");
    }
}
