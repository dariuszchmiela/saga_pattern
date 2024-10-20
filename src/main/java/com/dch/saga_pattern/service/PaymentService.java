package com.dch.saga_pattern.service;

import com.dch.saga_pattern.model.Payment;
import com.dch.saga_pattern.model.PaymentDto;
import com.dch.saga_pattern.storage.entity.PaymentEntity;
import com.dch.saga_pattern.storage.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final SagaOrchestrator<PaymentDto, PaymentEntity> sagaOrchestrator;

    public PaymentService(PaymentRepository paymentRepository, CreatePaymentSagaStep createPaymentSagaStep) {
        this.paymentRepository = paymentRepository;
        this.sagaOrchestrator = new SagaOrchestrator<>();
        this.sagaOrchestrator.addStep(createPaymentSagaStep);
    }

    public Optional<Payment> getPaymentById(UUID paymentId) {
        Objects.requireNonNull(paymentId, "Payment Id must not be null");
        return paymentRepository.findByPaymentId(paymentId).map(this::toDto);
    }

    public Payment createPayment(PaymentDto createPaymentDto) {
        return toDto(sagaOrchestrator.execute(createPaymentDto));
    }

    @Transactional
    public void deletePayment(UUID paymentId) {
        paymentRepository.deleteByPaymentId(paymentId);
    }

    private Payment toDto(PaymentEntity entity) {
        return new Payment(entity.getId(), entity.getPaymentId(), entity.getStatus());
    }
}