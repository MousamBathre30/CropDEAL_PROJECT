package com.cropdeal.Orderservice.controller;

import com.cropdeal.Orderservice.entitty.Order;
import com.cropdeal.Orderservice.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
@Import(OrderControllerTest.TestConfig.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public OrderService orderService() {
            return mock(OrderService.class);  // manually mock
        }
    }

    @Test
    void testGetMessage() throws Exception {
        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(content().string("yes you are ready to place the order"));
    }

    @Test
    void testPlaceOrder() throws Exception {
        Order order = new Order();
        order.setId(1L);
        order.setCropId(2L);
        order.setDealerId(3L);
        order.setQuantity(10.0);

        when(orderService.placeOrder(any(Order.class))).thenReturn(order);

        mockMvc.perform(post("/orders/placeOrder")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testGetOrdersByDealerId() throws Exception {
        Order order = new Order();
        order.setId(1L);
        order.setDealerId(100L);

        when(orderService.getOrdersByDealerId(100L))
                .thenReturn(Collections.singletonList(order));

        mockMvc.perform(get("/orders/dealer/100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }
}
