package ru.otus.spring.service;

import org.springframework.messaging.support.ErrorMessage;

public interface ErrorHandler {

    void handleMessage(ErrorMessage errorMessage);
}
