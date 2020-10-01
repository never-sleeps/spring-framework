package ru.otus.spring.integration;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.spring.model.Order;

@MessagingGateway
public interface ShopGateway {
    @Gateway(requestChannel = "ordersChannel")
    void process(Order order);
}
