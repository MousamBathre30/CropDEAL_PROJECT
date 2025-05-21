package com.cropdeal.Orderservice.service;

import com.cropdeal.Orderservice.dto.CropDTO;
import com.cropdeal.Orderservice.entitty.Order;
import com.cropdeal.Orderservice.feignclient.CropClient;
import com.cropdeal.Orderservice.repo.OrderRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Getter
@Setter
public class OrderServiceImpl implements OrderService{


    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CropClient cropClient;

    @Autowired
    private RabbitTemplate rabbitTemplate;


    @Override
    public Order placeOrder(Order order) {
        List<CropDTO> crops = cropClient.getCropById(order.getCropId());

        if (crops == null || crops.isEmpty()) {
            throw new RuntimeException("Crop not found with ID: " + order.getCropId());
        }

        CropDTO crop = crops.get(0); // 🟢 Get the first crop (or handle multiple)

        if (crop.getQuantity() < order.getQuantity()) {
            throw new RuntimeException("Requested quantity is more than available.");
        }

        order.setFarmerId(crop.getFarmerId());
        order.setCropName(crop.getCropName());
        order.setCropType(crop.getCropType());
        order.setStatus("Pending");

        String notifyMessage = "Dealer " + order.getDealerId() + " ordered Crop ID " + order.getCropId();
        rabbitTemplate.convertAndSend("cropdeal-exchange", "order.placed", notifyMessage);
        return orderRepository.save(order);
    }


    @Override
    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));
    }

    @Override
    public List<Order> getAllOrders() {
        return    orderRepository.findAll();
    }

    @Override
    public List<Order> getOrdersByDealerId(Long dealerId) {
        return orderRepository.findByDealerId(dealerId);
    }

    @Override
    public List<Order> getPendingOrdersByDealerId(Long dealerId) {
        return orderRepository.findByDealerIdAndStatus(dealerId, "Pending");
    }

    @Override
    public List<Order> searchOrdersByCropId(Long cropId) {
        return orderRepository.findByCropId(cropId);
    }

    @Override
    public Order updateOrderStatus(Long orderId, String status) {
        Order order = getOrderById(orderId);
        order.setStatus(status);
        return orderRepository.save(order);
    }

    @Override
    public Order cancelOrder(Long orderId) {
        Order order = getOrderById(orderId);
        if ("Completed".equalsIgnoreCase(order.getStatus())) {
            throw new RuntimeException("Cannot cancel a completed order");
        }
        order.setStatus("Cancelled");
        return orderRepository.save(order);

    }

    @Override
    public void markAsCompleted(Long orderId) {
        Order order = getOrderById(orderId);
        if (!"Completed".equalsIgnoreCase(order.getStatus())) {
            order.setStatus("Completed");
            orderRepository.save(order);
        }

        String confirmationMessage = "You placed the order for Crop ID " + order.getCropId();
        rabbitTemplate.convertAndSend("cropdeal-exchange", "order.confirmation", confirmationMessage);

    }
}
