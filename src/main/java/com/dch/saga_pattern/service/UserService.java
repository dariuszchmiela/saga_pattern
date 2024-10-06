package com.dch.saga_pattern.service;

import com.dch.saga_pattern.exception.UserAlreadyExistException;
import com.dch.saga_pattern.exception.UserNotExistException;
import com.dch.saga_pattern.model.CreateUserDto;
import com.dch.saga_pattern.model.Order;
import com.dch.saga_pattern.model.Product;
import com.dch.saga_pattern.model.UserDto;
import com.dch.saga_pattern.storage.entity.OrderEntity;
import com.dch.saga_pattern.storage.entity.ProductEntity;
import com.dch.saga_pattern.storage.entity.UserEntity;
import com.dch.saga_pattern.storage.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(CreateUserDto dto) {
        Objects.requireNonNull(dto, "CreateUserDto cannot be null");
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new UserAlreadyExistException("UserDto with email " + dto.getEmail() + " already exists");
        }
        userRepository.save(toEntities(dto));
    }


    public Optional<UserDto> getUserByEmail(String email) {
        Objects.requireNonNull(email, "Email cannot be null");
        return userRepository.findByEmail(email).map(this::toDto);
    }

    private UserDto getUserByEmailOrThrow(String email) {
        return userRepository.findByEmail(email).map(this::toDto).orElseThrow(() -> new UserNotExistException("UserDto with email " + email + " does not exist"));
    }

    public UserDto toDto(UserEntity entity) {
        UserDto user = new UserDto(entity.getUserName(), entity.getEmail())
                .setId(entity.getId());
        user.setOrders(toOrderDtos(entity.getOrders()));
        return user;
    }

    private List<Order> toOrderDtos(Set<OrderEntity> entities) {
        if (entities == null) {
            return List.of();
        }
        return entities.stream().map(this::toDto).collect(Collectors.toList());
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

    private UserEntity toEntities(CreateUserDto dto) {
        return new UserEntity(
                dto.getUserName(),
                dto.getEmail(),
                Set.of() // Initialize with an empty set or the appropriate set of OrderEntity objects
        );
    }
}
