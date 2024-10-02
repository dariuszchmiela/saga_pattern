package com.dch.saga_pattern.service;

import com.dch.saga_pattern.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    private final Map<String, Order> orderStore = new HashMap<>();

    public Order createOrder(String productId) {
        Order order = new Order();
        order.setOrderId(String.valueOf(System.currentTimeMillis()));
        order.setProduct(productId);
        order.setStatus("pending");
        orderStore.put(order.getOrderId(), order);
        logger.info("Order created: {}", order);
        return order;
    }

    public Order getOrder(String orderId) {
        return orderStore.get(orderId);
    }

    public void completeOrder(String orderId) {
        Order order = orderStore.get(orderId);
        if (order != null) {
            order.setStatus("completed");
            logger.info("Order {} completed.", orderId);
        }
    }

    public void cancelOrder(String orderId) {
        Order order = orderStore.get(orderId);
        if (order != null) {
            order.setStatus("cancelled");
            logger.info("Order {} cancelled.", orderId);
        }
    }
}
