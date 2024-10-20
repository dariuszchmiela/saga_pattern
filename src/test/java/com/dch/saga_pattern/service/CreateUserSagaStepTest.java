package com.dch.saga_pattern.service;

import com.dch.saga_pattern.model.User;
import com.dch.saga_pattern.storage.entity.UserEntity;
import com.dch.saga_pattern.storage.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class CreateUserSagaStepTest {
    private static final String EMAIL = "ted.programmer@fakemail.com";
    @Autowired
    private CreateUserSagaStep createUserSagaStep;
    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void cleanUp() {
        userRepository.deleteAll();
    }

    @Test
    void execute() {
        // given
        User userDto = new User(EMAIL);
        // when
        UserEntity userEntity = createUserSagaStep.execute(userDto);
        // then
        assertNotNull(userEntity);
        assertNotNull(userEntity.getId());
        assertEquals(EMAIL, userEntity.getEmail());
        assertNotNull(userEntity.getUserId());
        assertNotNull(userEntity.getCreatedAt());
        assertNotNull(userEntity.getUpdatedAt());
        assertNotNull(userEntity.getVersion());
    }

    @Test
    void compensate() {
        // given
        User userDto = new User(EMAIL);
        userRepository.save(new UserEntity(userDto.getEmail()));
        // when
        createUserSagaStep.compensate(userDto);
        // then
        assertEquals(0, userRepository.count());
    }
}