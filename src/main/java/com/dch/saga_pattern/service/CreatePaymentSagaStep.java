package com.dch.saga_pattern.service;

import com.dch.saga_pattern.exception.PaymentAlreadyExistException;
import com.dch.saga_pattern.model.PaymentDto;
import com.dch.saga_pattern.storage.entity.OrderEntity;
import com.dch.saga_pattern.storage.entity.PaymentEntity;
import com.dch.saga_pattern.storage.repository.OrderRepository;
import com.dch.saga_pattern.storage.repository.PaymentRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CreatePaymentSagaStep implements SagaStep<PaymentDto, PaymentEntity> {
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    public CreatePaymentSagaStep(PaymentRepository paymentRepository, OrderRepository orderRepository) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public PaymentEntity execute(PaymentDto data) {
        if (paymentRepository.findByPaymentId(data.getPaymentId()).isPresent()) {
            throw new PaymentAlreadyExistException("Payment with id " + data.getPaymentId() + " already exists");
        }
        OrderEntity orderEntity = orderRepository.findByOrderId(data.getOrderId())
                .orElseThrow(() -> new IllegalArgumentException("Order with id " + data.getOrderId() + " not found"));
        PaymentEntity paymentEntity = new PaymentEntity(data.getPaymentId(), data.getAmount(), data.getPaymentStatus(), orderEntity);
        return paymentRepository.save(paymentEntity);
    }

    @Override
    @Transactional
    public void compensate(PaymentDto data) {
        paymentRepository.deleteByPaymentId(data.getPaymentId());
    }
}