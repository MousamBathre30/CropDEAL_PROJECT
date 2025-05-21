package com.cropdealer.CropService.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String CROP_QUEUE = "crop-service-queue";
    public static final String EXCHANGE_NAME = "cropdeal-exchange";
    public static final String ROUTING_KEY = "payment.crop";  // previously "order.crop"

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue cropQueue() {
        return new Queue(CROP_QUEUE, false); // false = non-durable, adjust as needed
    }

    @Bean
    public Binding binding(Queue cropQueue, TopicExchange exchange) {
        return BindingBuilder.bind(cropQueue).to(exchange).with("payment.*");
    }
}
