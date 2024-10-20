package com.dch.saga_pattern.storage.repository;

import com.dch.saga_pattern.storage.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    Optional<OrderEntity> findByOrderId(UUID orderId);

    void deleteByOrderId(UUID orderId);
}
