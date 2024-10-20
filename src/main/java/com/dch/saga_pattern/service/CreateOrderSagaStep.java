package com.dch.saga_pattern.service;

import com.dch.saga_pattern.exception.OrderAlreadyExistException;
import com.dch.saga_pattern.exception.ProductNotExistException;
import com.dch.saga_pattern.exception.UserNotExistException;
import com.dch.saga_pattern.model.OrderDto;
import com.dch.saga_pattern.storage.entity.OrderEntity;
import com.dch.saga_pattern.storage.entity.ProductEntity;
import com.dch.saga_pattern.storage.entity.UserEntity;
import com.dch.saga_pattern.storage.repository.OrderRepository;
import com.dch.saga_pattern.storage.repository.ProductRepository;
import com.dch.saga_pattern.storage.repository.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Component
public class CreateOrderSagaStep implements SagaStep<OrderDto, OrderEntity> {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public CreateOrderSagaStep(OrderRepository orderRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Override
    public OrderEntity execute(OrderDto data) {
        UserEntity user = userRepository.findByUserId(data.getUserId()).orElseThrow(() -> new UserNotExistException("UserDto with id " + data.getUserId() + " does not exist"));
        Set<ProductEntity> products = productRepository.findByProductIdIn(data.getProductIds());
        if (products.isEmpty()) {
            throw new ProductNotExistException("Product with ids " + data.getProductIds() + " does not exist");
        }
        if (orderRepository.findByOrderId(data.getOrderId()).isPresent()) {
            throw new OrderAlreadyExistException("Order with orderId " + data.getOrderId() + " already exists");
        }
        OrderEntity orderEntity = new OrderEntity(data.getOrderId(), user, products);
        return orderRepository.save(orderEntity);
    }

    @Override
    @Transactional
    public void compensate(OrderDto data) {
        orderRepository.deleteByOrderId(data.getOrderId());
    }
}