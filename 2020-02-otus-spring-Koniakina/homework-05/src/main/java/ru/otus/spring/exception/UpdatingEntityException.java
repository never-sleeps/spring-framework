package ru.otus.spring.exception;

public class UpdatingEntityException extends RuntimeException {
    public UpdatingEntityException(String message) {
        super(message);
    }
}
