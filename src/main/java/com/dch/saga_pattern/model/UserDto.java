package com.dch.saga_pattern.model;

import java.util.List;

public class UserDto {
    private Long id;
    private String userName;
    private String email;
    private List<Order> orders;

    public UserDto() {
    }

    public UserDto(String userName, String email) {
        this.userName = userName;
        this.email = email;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public UserDto setOrders(List<Order> orders) {
        this.orders = orders;
        return this;
    }

    public Long getId() {
        return id;
    }

    public UserDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public UserDto setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserDto setEmail(String email) {
        this.email = email;
        return this;
    }
}