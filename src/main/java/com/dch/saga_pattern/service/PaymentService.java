package com.dch.saga_pattern.service;

import com.dch.saga_pattern.exception.PaymentFailedException;
import com.dch.saga_pattern.model.Payment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);

    public Payment processPayment(String orderId) {
        Payment payment = new Payment();
        payment.setPaymentId(String.valueOf(System.currentTimeMillis()));
        payment.setOrderId(orderId);

        // Symulacja przypadkowego sukcesu lub porażki
        boolean paymentSuccess = Math.random() > 0.5;

        if (paymentSuccess) {
            payment.setStatus("completed");
            logger.info("Payment completed for order: {}", orderId);
        } else {
            payment.setStatus("failed");
            logger.info("Payment failed for order: {}", orderId);
            throw new PaymentFailedException("Payment processing failed for order: " + orderId);
        }

        return payment;
    }
}
