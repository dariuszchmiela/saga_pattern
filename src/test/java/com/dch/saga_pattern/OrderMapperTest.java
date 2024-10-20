package com.dch.saga_pattern;

import com.dch.saga_pattern.mapper.OrderMapper;
import com.dch.saga_pattern.model.Order;
import com.dch.saga_pattern.storage.entity.OrderEntity;
import com.dch.saga_pattern.storage.entity.ProductEntity;
import com.dch.saga_pattern.storage.entity.UserEntity;
import com.dch.saga_pattern.util.OrderTestDataUtil;
import com.dch.saga_pattern.util.ProductTestDataUtil;
import com.dch.saga_pattern.util.UserTestDataUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class OrderMapperTest {

    @Test
    void toDto() {
        // given
        UserEntity user = UserTestDataUtil.createTestUser();
        ProductEntity product = ProductTestDataUtil.createProductA();
        OrderEntity entity = OrderTestDataUtil.createTestOrder(user, product);
        // when
        final Order result = OrderMapper.toDto(entity);
        // then
        assertNotNull(result);
        assertNotNull(result.getOrderId());
        assertNotNull(result.getProducts());
        assertEquals(1, result.getProducts().size());

        var resultProduct = result.getProducts().iterator().next();
        assertEquals(product.getId(), resultProduct.getId());
        assertEquals(product.getProductId(), resultProduct.getProductId());
        assertEquals(product.getName(), resultProduct.getName());
        assertEquals(product.getPrice(), resultProduct.getPrice());
        assertEquals(product.getType(), resultProduct.getType());

        var userResults = result.getUser();
        assertEquals(user.getUserId(), userResults.getUserId());
        assertEquals(user.getEmail(), userResults.getEmail());
        assertEquals(user.getName(), userResults.getName());

    }
}