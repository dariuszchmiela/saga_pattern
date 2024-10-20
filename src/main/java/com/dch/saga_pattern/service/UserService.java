package com.dch.saga_pattern.service;

import com.dch.saga_pattern.model.Order;
import com.dch.saga_pattern.model.Product;
import com.dch.saga_pattern.model.User;
import com.dch.saga_pattern.model.UserDto;
import com.dch.saga_pattern.storage.entity.OrderEntity;
import com.dch.saga_pattern.storage.entity.ProductEntity;
import com.dch.saga_pattern.storage.entity.UserEntity;
import com.dch.saga_pattern.storage.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final SagaOrchestrator<User, UserEntity> sagaOrchestrator;

    public UserService(UserRepository userRepository, CreateUserSagaStep createUserSagaStep) {
        this.userRepository = userRepository;
        this.sagaOrchestrator = new SagaOrchestrator<>();
        this.sagaOrchestrator.addStep(createUserSagaStep);
    }

    public User createUser(User dto) {
        Objects.requireNonNull(dto, "User cannot be null");
        return toDtoWithOrders(sagaOrchestrator.execute(dto));
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserByEmail(String email) {
        Objects.requireNonNull(email, "Email cannot be null");
        return userRepository.findByEmail(email).map(this::toDtoWithOrders);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserById(UUID userId) {
        Objects.requireNonNull(userId, "UserId cannot be null");
        return userRepository.findByUserId(userId).map(this::toDtoWithOrders);
    }

    @Transactional
    public void deleteUser(UUID userId) {
        Objects.requireNonNull(userId, "UserId cannot be null");
        userRepository.deleteByUserId(userId);
    }

    private User toDtoWithOrders(UserEntity entity) {
        User user = new User(entity.getName(), entity.getEmail());
        user.setOrders(toOrderDtos(entity.getOrders()));
        return user;
    }

    private List<Order> toOrderDtos(Set<OrderEntity> entities) {
        if (entities == null) {
            return List.of();
        }
        return entities.stream().map(this::toDtoWithOrders).collect(Collectors.toList());
    }

    private Order toDtoWithOrders(OrderEntity entity) {
        if (entity == null) {
            return null;
        }
        return new Order(entity.getId(), entity.getOrderId(), toProductDtos(entity.getProducts()), toUser(entity.getUser()));
    }

    private Set<Product> toProductDtos(Set<ProductEntity> entities) {
        if (entities == null) {
            return Set.of();
        }
        return entities.stream().map(this::toDtoWithOrders).collect(Collectors.toSet());
    }

    private Product toDtoWithOrders(ProductEntity entity) {
        if (entity == null) {
            return null;
        }
        return new Product(entity.getId(), entity.getProductId(), entity.getName(), entity.getPrice(), entity.getType());
    }

    private UserDto toUser(UserEntity entity) {
        return new UserDto(entity.getUserId(), entity.getEmail(), entity.getName());
    }
}