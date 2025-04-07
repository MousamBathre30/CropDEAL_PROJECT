package com.cropDeal.PaymentService.config;

import com.cropDeal.PaymentService.dto.PaymentMessageDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentMessageListener {

    @RabbitListener(queues = "payment-confirmation-queue")
    public void receiveMessage(PaymentMessageDTO message) {
        System.out.println(" Received message from RabbitMQ:");
        System.out.println("Order ID: " + message.getOrderId());
        System.out.println("Status: " + message.getStatus());
        System.out.println("Message: " + message.getMessage());
    }
}
