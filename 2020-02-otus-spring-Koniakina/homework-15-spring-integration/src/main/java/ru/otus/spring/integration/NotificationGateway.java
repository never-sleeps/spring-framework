package ru.otus.spring.integration;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.spring.model.Notification;

@MessagingGateway
public interface NotificationGateway {
    @Gateway(requestChannel = "notificationChannel")
    void process(Notification notification);
}
