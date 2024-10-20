package com.dch.saga_pattern.storage.repository;

import com.dch.saga_pattern.storage.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByUserId(UUID userId);

    void deleteByEmail(String email);

    void deleteByUserId(UUID userId);
}
