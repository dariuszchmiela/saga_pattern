package com.dch.saga_pattern.storage.repository;

import com.dch.saga_pattern.storage.entity.UserEntity;
import com.dch.saga_pattern.util.UserTestDataUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ExtendWith(SpringExtension.class)
class UserRepositoryTest {
    public static final String EMAIL = "radzio.programmer@fakemail.com";
    public static final UserEntity TEST_USER = UserTestDataUtil.createTestUser(EMAIL);
    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void cleanUp() {
        userRepository.deleteAll();
    }

    @Test
    void findByEmail() {
        // given
        var saved = userRepository.save(TEST_USER);
        // when
        var found = userRepository.findByEmail(EMAIL);
        // then
        assertNotNull(found);
        assertTrue(found.isPresent());
        assertEquals(saved.getId(), found.get().getId());
        assertEquals(saved.getEmail(), found.get().getEmail());
    }

    @Test
    void findByUserId() {
        // given
        var saved = userRepository.save(TEST_USER);
        // when
        var found = userRepository.findByUserId(saved.getUserId());
        // then
        assertNotNull(found);
        assertTrue(found.isPresent());
        assertEquals(saved.getId(), found.get().getId());
        assertEquals(saved.getUserId(), found.get().getUserId());
    }

    @Test
    void testSave() {
        // given & when
        var saved = userRepository.save(TEST_USER);
        // then
        assertNotNull(saved.getId());

        assertEquals(TEST_USER.getEmail(), saved.getEmail());
        assertEquals(TEST_USER.getUserId(), saved.getUserId());

        assertNotNull(saved.getId());
        assertNotNull(saved.getUserId());
        assertNotNull(saved.getCreatedAt());
        assertNotNull(saved.getUpdatedAt());
        assertNotNull(saved.getVersion());
    }
}