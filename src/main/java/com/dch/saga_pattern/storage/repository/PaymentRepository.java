package com.dch.saga_pattern.storage.repository;

import com.dch.saga_pattern.storage.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
    Optional<PaymentEntity> findByPaymentId(UUID paymentId);

    void deleteByPaymentId(UUID paymentId);
}
