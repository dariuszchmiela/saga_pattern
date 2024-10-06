package com.dch.saga_pattern.service;

public interface SagaStep<T> {
    void execute(T data);

    void compensate(T data);
}
