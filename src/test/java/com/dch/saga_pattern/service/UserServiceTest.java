package com.dch.saga_pattern.service;

import com.dch.saga_pattern.exception.UserAlreadyExistException;
import com.dch.saga_pattern.model.CreateUserDto;
import com.dch.saga_pattern.storage.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class UserServiceTest {
    public static final String MARIO_EMAIL = "mario@fakemail.com";
    public static final String MARIO_NAME = "Mario";
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @AfterEach
    void cleanUp() {
        userRepository.deleteAll();
    }

    @Test
    @Transactional
    void createUser() {
        // given
        assertTrue(userRepository.findAll().isEmpty());
        // when
        userService.createUser(new CreateUserDto(MARIO_NAME, MARIO_EMAIL));
        // then
        assertThat(userRepository.findByEmail(MARIO_EMAIL))
                .isPresent()
                .get()
                .satisfies(user -> {
                    assertThat(user.getUserName()).isEqualTo(MARIO_NAME);
                    assertThat(user.getEmail()).isEqualTo(MARIO_EMAIL);
                    assertThat(user.getId()).isNotNull();
                    assertThat(user.getCreatedAt()).isNotNull();
                    assertThat(user.getUpdatedAt()).isNotNull();
                    assertThat(user.getOrders()).isNotNull();
                    assertThat(user.getOrders()).isEmpty();
                });
    }

    @Test
    @Transactional
    void createUserAlreadyExists() {
        // given
        userService.createUser(new CreateUserDto(MARIO_NAME, MARIO_EMAIL));
        // when & then
        assertThatThrownBy(() -> userService.createUser(new CreateUserDto(MARIO_NAME, MARIO_EMAIL)))
                .isInstanceOf(UserAlreadyExistException.class)
                .hasMessageContaining("UserDto with email " + MARIO_EMAIL + " already exists");
    }

    @Test
    @Transactional
    void getUserByEmail() {
        // given
        assertTrue(userRepository.findAll().isEmpty());
        userService.createUser(new CreateUserDto(MARIO_NAME, MARIO_EMAIL));
        // when
        var user = userService.getUserByEmail(MARIO_EMAIL);
        // then
        assertThat(user)
                .isPresent()
                .get()
                .satisfies(u -> {
                    assertThat(u.getUserName()).isEqualTo(MARIO_NAME);
                    assertThat(u.getEmail()).isEqualTo(MARIO_EMAIL);
                    assertThat(u.getId()).isNotNull();
                    assertThat(u.getOrders()).isNotNull();
                    assertThat(u.getOrders()).isEmpty();
                });
    }
}