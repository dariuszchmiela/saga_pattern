package com.dch.saga_pattern.storage.repository;

import com.dch.saga_pattern.storage.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    Optional<ProductEntity> findByProductId(UUID productId);

    Set<ProductEntity> findByProductIdIn(Set<UUID> productIds);

    void deleteByProductId(UUID productId);
}
