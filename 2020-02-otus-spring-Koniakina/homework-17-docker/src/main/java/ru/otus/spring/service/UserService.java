package ru.otus.spring.service;

import ru.otus.spring.model.secuirity.User;

import java.util.Optional;

public interface UserService {

    Optional<User> findByUsername(String username);
}
