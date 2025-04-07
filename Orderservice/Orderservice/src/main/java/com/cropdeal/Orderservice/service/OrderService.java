package com.cropdeal.Orderservice.service;




import com.cropdeal.Orderservice.entitty.Order;

import java.util.List;

public interface OrderService {


        Order placeOrder(Order order);
        Order getOrderById(Long orderId);
        List<Order> getAllOrders();
        List<Order> getOrdersByDealerId(Long dealerId);
        List<Order> getPendingOrdersByDealerId(Long dealerId);
        List<Order> searchOrdersByCropId(Long cropId);
        Order updateOrderStatus(Long orderId, String status);
        Order cancelOrder(Long orderId);
        void markAsCompleted(Long orderId);


}

