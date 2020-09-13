package dao;

import entity.Question;

public interface QuestionDao {
    Question next();
    Question current();
    int getCount();
}
