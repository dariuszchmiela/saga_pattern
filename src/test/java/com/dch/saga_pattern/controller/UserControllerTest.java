package com.dch.saga_pattern.controller;

import com.dch.saga_pattern.model.User;
import com.dch.saga_pattern.storage.entity.UserEntity;
import com.dch.saga_pattern.storage.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    private static final String BASE_PATH = "/api/users";
    private static final String EMAIL = "john.programmer@fakemail.com";
    private static final String JOHN_PROGRAMMER = "John Programmer";
    public static final User USER_DTO = new User(EMAIL).setUserName(JOHN_PROGRAMMER);
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void cleanUp() {
        userRepository.deleteAll();
    }

    @Test
    void createUser() throws Exception {
        // given when & then
        mvc.perform(post(BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(USER_DTO)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userName").value(JOHN_PROGRAMMER))
                .andExpect(jsonPath("$.email").value(EMAIL));
    }

    @Test
    void getUser() throws Exception {
        // given
        mvc.perform(post(BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(USER_DTO)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userName").value(JOHN_PROGRAMMER))
                .andExpect(jsonPath("$.email").value(EMAIL));
        Optional<UserEntity> userOpt = userRepository.findByEmail(EMAIL);
        assertTrue(userOpt.isPresent());
        UserEntity user = userOpt.get();
        // when & then
        mvc.perform(get(BASE_PATH + "/{userId}", user.getUserId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").value(JOHN_PROGRAMMER))
                .andExpect(jsonPath("$.email").value(EMAIL));
    }

    @Test
    void deleteUser() throws Exception {
        // given
        mvc.perform(post(BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(USER_DTO)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userName").value(JOHN_PROGRAMMER))
                .andExpect(jsonPath("$.email").value(EMAIL));
        Optional<UserEntity> userOpt = userRepository.findByEmail(EMAIL);
        assertTrue(userOpt.isPresent());
        UserEntity user = userOpt.get();
        // when & then
        mvc.perform(delete(BASE_PATH + "/{userId}", user.getUserId()))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}