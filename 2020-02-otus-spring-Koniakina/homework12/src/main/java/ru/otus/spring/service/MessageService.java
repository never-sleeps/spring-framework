package ru.otus.spring.service;

public interface MessageService {
    String getLocaleMessage(String message, Object... objects);
}
