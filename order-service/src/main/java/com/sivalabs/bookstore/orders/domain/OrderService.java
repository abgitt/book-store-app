package com.sivalabs.bookstore.orders.domain;

import com.sivalabs.bookstore.orders.domain.models.CreateOrderRequest;
import com.sivalabs.bookstore.orders.domain.models.CreateOrderResponse;
import com.sivalabs.bookstore.orders.domain.models.OrderCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderEventService orderEventService;
    private static final Logger log = LoggerFactory.getLogger(OrderService.class);
    private final OrderValidator orderVaidator;

    public OrderService(
            OrderRepository orderRepository,
            OrderEventRepository orderEventRepository,
            OrderEventService orderEventService,
            OrderValidator orderVaidator) {
        this.orderRepository = orderRepository;
        this.orderEventService = orderEventService;
        this.orderVaidator = orderVaidator;
    }

    public CreateOrderResponse createOrder(String userName, CreateOrderRequest request) {
        orderVaidator.validate(request);
        OrderEntity newOrder = OrderMapper.convertToEntity(request);
        newOrder.setUserName(userName);
        OrderEntity savedOrder = this.orderRepository.save(newOrder);
        log.info("Created Order with orderNumber={}", savedOrder.getOrderNumber());
        OrderCreatedEvent orderCreatedEvent = OrderEventMapper.buildOrderCreatedEvent(savedOrder);
        orderEventService.save(orderCreatedEvent);
        return new CreateOrderResponse(savedOrder.getOrderNumber());
    }
}
