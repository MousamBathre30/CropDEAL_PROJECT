package com.cropdealer.CropService.listner;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class CropOrderListener {

    @RabbitListener(queues = "crop-service-queue")
    public void handleCropOrder(String message) {
        System.out.println("🌾 Crop Service received order event: " + message);
    }
}
