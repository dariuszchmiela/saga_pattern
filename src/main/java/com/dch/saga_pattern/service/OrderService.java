package com.dch.saga_pattern.service;

import com.dch.saga_pattern.exception.OrderAlreadyExistException;
import com.dch.saga_pattern.model.CreateOrderDto;
import com.dch.saga_pattern.model.Order;
import com.dch.saga_pattern.model.Product;
import com.dch.saga_pattern.storage.entity.OrderEntity;
import com.dch.saga_pattern.storage.entity.ProductEntity;
import com.dch.saga_pattern.storage.repository.OrderRepository;
import com.dch.saga_pattern.storage.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    public Order createOrder(CreateOrderDto dto) {
        if (orderRepository.findByOrderId(dto.getOrderId()).isPresent()) {
            throw new OrderAlreadyExistException("Order with orderId " + dto.getOrderId() + " already exists");
        }
        OrderEntity orderEntity = toEntity(dto);
        return toDto(orderRepository.save(orderEntity));
    }

    private Order toDto(OrderEntity entity) {
        return new Order(entity.getId(), entity.getOrderId(), toProductDtos(entity.getProducts()));
    }

    private Set<Product> toProductDtos(Set<ProductEntity> entities) {
        if (entities == null) {
            return Set.of();
        }
        return entities.stream().map(this::toDto).collect(Collectors.toSet());
    }

    private Product toDto(ProductEntity entity) {
        if (entity == null) {
            return null;
        }
        return new Product(entity.getId(), entity.getProductId(), entity.getName(), entity.getPrice(), entity.getProductType());
    }

    private OrderEntity toEntity(CreateOrderDto dto) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderId(dto.getOrderId());

        Set<ProductEntity> products = productRepository.findByProductIdIn(dto.getProductIds());
        orderEntity.setProducts(products);

        return orderEntity;
    }
}
