package ru.otus.spring.exception;

public class EntityCreateException extends RuntimeException{

    public EntityCreateException(String message) {
        super(message);
    }
}
