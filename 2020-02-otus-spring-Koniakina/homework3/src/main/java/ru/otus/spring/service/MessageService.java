package ru.otus.spring.service;

public interface MessageService {
    String getMessage(String message, Object ...objects);
}