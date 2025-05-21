package com.cropdeal.Orderservice.listner;

import com.cropdeal.Orderservice.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class MessageListener {

    @RabbitListener(queues = RabbitMQConfig.ORDER_QUEUE)
    public void receive(String message) {
        System.out.println("✅ Order Service Message: " + message);
    }
}
