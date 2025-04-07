package com.cropDeal.PaymentService.service;

import com.cropDeal.PaymentService.dto.PaymentMessageDTO;
import com.cropDeal.PaymentService.entity.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class RabbitMQSender {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.routingkey}")
    private String routingKey;

    public RabbitMQSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendPaymentMessage(Payment payment) {
        rabbitTemplate.convertAndSend(exchange, routingKey, payment);
    }

    public void send(PaymentMessageDTO messageDTO) {
        rabbitTemplate.convertAndSend(exchange, routingKey, messageDTO);
    }
}
