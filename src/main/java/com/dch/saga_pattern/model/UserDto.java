package com.dch.saga_pattern.model;

import java.util.UUID;

public class UserDto {
    private UUID userId;
    private String email;
    private String name;

    public UserDto() {
    }

    public UserDto(UUID userId, String email, String name) {
        this.userId = userId;
        this.email = email;
        this.name = name;
    }

    public UUID getUserId() {
        return userId;
    }

    public UserDto setUserId(UUID userId) {
        this.userId = userId;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getName() {
        return name;
    }

    public UserDto setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "userId=" + userId +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
