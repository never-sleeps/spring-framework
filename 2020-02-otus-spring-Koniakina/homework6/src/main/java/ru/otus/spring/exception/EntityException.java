package ru.otus.spring.exception;

public class EntityException extends RuntimeException {
    public EntityException(String message) {
        super(message);
    }
}
