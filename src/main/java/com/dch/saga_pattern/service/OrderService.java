package com.dch.saga_pattern.service;

import com.dch.saga_pattern.mapper.OrderMapper;
import com.dch.saga_pattern.model.Order;
import com.dch.saga_pattern.model.OrderDto;
import com.dch.saga_pattern.storage.entity.OrderEntity;
import com.dch.saga_pattern.storage.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final SagaOrchestrator<OrderDto, OrderEntity> sagaOrchestrator;

    public OrderService(OrderRepository orderRepository, CreateOrderSagaStep createOrderSagaStep) {
        this.orderRepository = orderRepository;
        this.sagaOrchestrator = new SagaOrchestrator<>();
        this.sagaOrchestrator.addStep(createOrderSagaStep);
    }

    @Transactional(readOnly = true)
    public Optional<Order> getOrderById(UUID orderId) {
        return orderRepository.findByOrderId(orderId).map(OrderMapper::toDto);
    }

    @Transactional
    public Order createOrder(OrderDto dto) {
        return OrderMapper.toDto(sagaOrchestrator.execute(dto));
    }

    @Transactional
    public void deleteOrder(UUID orderId) {
        orderRepository.deleteByOrderId(orderId);
    }
}