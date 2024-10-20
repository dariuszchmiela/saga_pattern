package com.dch.saga_pattern.service;

import com.dch.saga_pattern.exception.UserAlreadyExistException;
import com.dch.saga_pattern.model.User;
import com.dch.saga_pattern.storage.entity.UserEntity;
import com.dch.saga_pattern.storage.repository.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CreateUserSagaStep implements SagaStep<User, UserEntity> {
    private final UserRepository userRepository;

    public CreateUserSagaStep(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserEntity execute(User data) {
        if (userRepository.findByEmail(data.getEmail()).isPresent()) {
            throw new UserAlreadyExistException("UserDto with email " + data.getEmail() + " already exists");
        }
        UserEntity userEntity = new UserEntity(data.getEmail());
        userEntity.setName(data.getUserName());
        return userRepository.save(userEntity);
    }

    @Override
    @Transactional
    public void compensate(User data) {
        userRepository.deleteByEmail(data.getEmail());
    }
}