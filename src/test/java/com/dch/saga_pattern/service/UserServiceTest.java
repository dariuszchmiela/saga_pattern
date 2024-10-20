package com.dch.saga_pattern.service;

import com.dch.saga_pattern.model.User;
import com.dch.saga_pattern.storage.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class UserServiceTest {
    private static final String EMAIL = "robot.programmer@fakemail.com";
    public static final User USER_DTO = new User(EMAIL);
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void cleanUp() {
        userRepository.deleteAll();
    }

    @Test
    void createUser() {
        // given & when
        User user = userService.createUser(USER_DTO);
        // then
        assertNotNull(user);
        assertEquals(EMAIL, user.getEmail());
    }

    @Test
    void getUserByEmail() {
        // given
        userService.createUser(USER_DTO);
        // when
        Optional<User> userOpt = userService.getUserByEmail(EMAIL);
        // then
        assertTrue(userOpt.isPresent());
        assertEquals(EMAIL, userOpt.get().getEmail());
    }
}