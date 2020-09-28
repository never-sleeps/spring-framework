package ru.otus.spring.exception;

public class PostException extends RuntimeException {
    public PostException(String message) {
        super(message);
    }
}
