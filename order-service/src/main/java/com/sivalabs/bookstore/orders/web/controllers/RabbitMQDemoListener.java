package com.sivalabs.bookstore.orders.web.controllers;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQDemoListener {
    @RabbitListener(queues = "${orders.new-orders-queue}")
    public void handleNewOrder(RabbitMQDemoController.MyPayLoad payLoad) {
        System.out.println("New order :" + payLoad.content());
    }

    @RabbitListener(queues = "${orders.delivered-orders-queue}")
    public void handleDeliveredOrder(RabbitMQDemoController.MyPayLoad payLoad) {
        System.out.println("Delivered order :" + payLoad.content());
    }
}
