package ru.otus.spring.dao;

import ru.otus.spring.model.Question;

public interface QuestionDao {
    Question next();
    Question current();
    int getCount();
}
