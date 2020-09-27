package ru.otus.spring.integration;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.spring.model.Order;
import ru.otus.spring.model.Parcel;

@MessagingGateway
public interface ShopGateway {
    @Gateway(requestChannel = "ordersChannel", replyChannel = "parcelsChannel")
    Parcel process(Order order);
}
