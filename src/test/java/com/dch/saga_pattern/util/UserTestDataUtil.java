package com.dch.saga_pattern.util;

import com.dch.saga_pattern.storage.entity.UserEntity;

import java.util.UUID;

public class UserTestDataUtil {
    private static final String TEST_EMAIL = "test.pogrammer@fakemail.com";

    public static UserEntity createTestUser(String email) {
        UserEntity user = new UserEntity();
        user.setUserId(UUID.randomUUID());
        user.setEmail(email);
        return user;
    }

    public static UserEntity createTestUser() {
        return createTestUser(TEST_EMAIL);
    }

    private UserTestDataUtil() {
        throw new IllegalStateException("Test data util class");
    }
}
