package com.CropDeal.NotificationService.listner;

import com.CropDeal.NotificationService.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class MessageListener {

    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void receive(String message) {
        System.out.println("🔔 Notification: " + message);
    }
}
