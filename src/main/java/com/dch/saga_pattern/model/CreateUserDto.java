package com.dch.saga_pattern.model;

public class CreateUserDto {
    private final String userName;
    private final String email;

    public CreateUserDto(String userName, String email) {
        this.userName = userName;
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }
}
