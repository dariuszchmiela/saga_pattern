package com.dch.saga_pattern.service;

import java.util.ArrayList;
import java.util.List;

public class SagaOrchestrator {
    private final List<SagaStep<T>> steps = new ArrayList<>();
    private int currentStep = -1;
}
