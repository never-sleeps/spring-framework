package ru.otus.spring.exception;

public class CreatingEntityException extends RuntimeException{

    public CreatingEntityException(String message) {
        super(message);
    }
}
