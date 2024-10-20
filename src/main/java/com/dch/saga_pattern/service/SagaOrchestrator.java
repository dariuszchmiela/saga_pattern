package com.dch.saga_pattern.service;

import java.util.ArrayList;
import java.util.List;

public class SagaOrchestrator<T, R> {
    private final List<SagaStep<T, R>> steps = new ArrayList<>();
    private int currentStep = -1;

    public void addStep(SagaStep<T, R> step) {
        steps.add(step);
    }

    public R execute(T data) {
        R result = null;
        try {
            for (currentStep = 0; currentStep < steps.size(); currentStep++) {
                result = steps.get(currentStep).execute(data);
            }
        } catch (Exception e) {
            compensate(data);
            throw e;
        }
        return result;
    }

    private void compensate(T data) {
        for (int i = currentStep; i >= 0; i--) {
            steps.get(i).compensate(data);
        }
    }
}