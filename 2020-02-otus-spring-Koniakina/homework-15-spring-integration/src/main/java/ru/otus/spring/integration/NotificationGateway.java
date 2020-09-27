package ru.otus.spring.integration;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.handler.annotation.Payload;
import ru.otus.spring.model.Notification;

@MessagingGateway
public interface NotificationGateway {

    @Gateway(requestChannel = "notificationChannel")
    void process(@Payload Notification notification);
}
