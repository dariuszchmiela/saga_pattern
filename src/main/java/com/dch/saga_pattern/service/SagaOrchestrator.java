package com.dch.saga_pattern.service;

import com.dch.saga_pattern.exception.PaymentFailedException;
import com.dch.saga_pattern.model.Order;
import com.dch.saga_pattern.model.Payment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SagaOrchestrator {

    private static final Logger logger = LoggerFactory.getLogger(SagaOrchestrator.class);

    private final OrderService orderService;
    private final PaymentService paymentService;

    public SagaOrchestrator(OrderService orderService, PaymentService paymentService) {
        this.orderService = orderService;
        this.paymentService = paymentService;
    }

    public Order createOrderSaga(String productId) {
        // 1. Utworzenie zamówienia
        Order order = orderService.createOrder(productId);

        try {
            // 2. Płatność za zamówienie
            Payment payment = paymentService.processPayment(order.getOrderId());

            if ("completed".equals(payment.getStatus())) {
                // 3. Zakończenie zamówienia po pomyślnej płatności
                orderService.completeOrder(order.getOrderId());
            } else {
                // 4. Anulowanie zamówienia w przypadku niepowodzenia płatności
                orderService.cancelOrder(order.getOrderId());
            }
        } catch (PaymentFailedException e) {
            // W przypadku błędu anuluj zamówienie
            logger.error("Error processing payment for order {}: {}", order.getOrderId(), e.getMessage());
            orderService.cancelOrder(order.getOrderId());
        }

        return order;
    }

    public void cancelOrderSaga(String orderId) {
        orderService.cancelOrder(orderId);
        logger.info("Saga orchestrator cancelled order: {}", orderId);
    }
}

