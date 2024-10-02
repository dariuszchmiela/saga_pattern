package com.dch.saga_pattern.controller;

import com.dch.saga_pattern.model.Order;
import com.dch.saga_pattern.service.SagaOrchestrator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SagaOrchestrator sagaOrchestrator;

    @Test
    public void testCreateOrder() throws Exception {
        Order order = new Order("123", "Product-XYZ", "pending");
        when(sagaOrchestrator.createOrderSaga("Product-XYZ")).thenReturn(order);

        mockMvc.perform(post("/orders/create")
                        .param("product", "Product-XYZ")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testCancelOrder() throws Exception {
        mockMvc.perform(post("/orders/cancel/123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}