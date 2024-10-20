package com.dch.saga_pattern.mapper;

import com.dch.saga_pattern.model.Order;
import com.dch.saga_pattern.model.Product;
import com.dch.saga_pattern.model.UserDto;
import com.dch.saga_pattern.storage.entity.OrderEntity;
import com.dch.saga_pattern.storage.entity.ProductEntity;
import com.dch.saga_pattern.storage.entity.UserEntity;

import java.util.Set;
import java.util.stream.Collectors;

public class OrderMapper {
    public static Product toDto(ProductEntity entity) {
        if (entity == null) {
            return null;
        }
        return new Product(entity.getId(), entity.getProductId(), entity.getName(), entity.getPrice(), entity.getType());
    }

    public static Order toDto(OrderEntity entity) {
        return new Order(entity.getId(), entity.getOrderId(), toProductDtos(entity.getProducts()), toUser(entity.getUser()));
    }

    private static Set<Product> toProductDtos(Set<ProductEntity> entities) {
        if (entities == null) {
            return Set.of();
        }
        return entities.stream().map(OrderMapper::toDto).collect(Collectors.toSet());
    }

    private static UserDto toUser(UserEntity entity) {
        return new UserDto(entity.getUserId(), entity.getEmail(), entity.getName());
    }

    private OrderMapper() {
        throw new IllegalStateException("Utility class");
    }
}
