package com.dch.saga_pattern.service;

public interface SagaStep<T, R> {
    R execute(T data);

    void compensate(T data);
}
