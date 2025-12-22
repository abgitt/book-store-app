package com.sivalabs.bookstore.orders.web.controllers;

import com.sivalabs.bookstore.orders.ApplicationProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class RabbitMQDemoController {
    private final RabbitTemplate rabbitTemplate;
    private final ApplicationProperties applicationProperties;

    RabbitMQDemoController(RabbitTemplate rabbitTemplate, ApplicationProperties applicationProperties) {
        this.rabbitTemplate = rabbitTemplate;
        this.applicationProperties = applicationProperties;
    }

    @PostMapping("/send")
    public void sendMessage(@RequestBody MyMessage message) {
        rabbitTemplate.convertAndSend(
                applicationProperties.orderEventsExchange(), message.routingKey(), message.payload());
    }

    record MyMessage(String routingKey, MyPayLoad payload) {}

    record MyPayLoad(String content) {}
}
