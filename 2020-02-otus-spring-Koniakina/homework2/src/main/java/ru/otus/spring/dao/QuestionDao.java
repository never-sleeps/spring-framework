package ru.otus.spring.dao;

import ru.otus.spring.entity.Question;

public interface QuestionDao {
    Question next();
    Question current();
    int getCount();
}
