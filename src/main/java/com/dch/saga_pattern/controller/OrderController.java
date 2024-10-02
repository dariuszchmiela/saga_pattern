package com.dch.saga_pattern.controller;

import com.dch.saga_pattern.model.Order;
import com.dch.saga_pattern.service.SagaOrchestrator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final SagaOrchestrator sagaOrchestrator;

    public OrderController(SagaOrchestrator sagaOrchestrator) {
        this.sagaOrchestrator = sagaOrchestrator;
    }

    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(@RequestParam String product) {
        Order order = sagaOrchestrator.createOrderSaga(product);
        return ResponseEntity.ok(order);
    }

    @PostMapping("/cancel/{orderId}")
    public ResponseEntity<Void> cancelOrder(@PathVariable String orderId) {
        sagaOrchestrator.cancelOrderSaga(orderId);
        return ResponseEntity.noContent().build();
    }
}
