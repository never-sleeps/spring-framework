package ru.otus.spring.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.spring.config.ApplicationProperties;
import ru.otus.spring.model.Question;

@DisplayName("Класс получения вопросов")
@SpringBootTest
public class QuestionDaoImplTest {

    @Autowired
    private ApplicationProperties applicationProperties;
    @Autowired
    private QuestionDao questionDao;

    @DisplayName("Проверка получения количества вопросов")
    @Test
    public void checkGetCount() {
        Assertions.assertEquals(5, questionDao.getCount());
    }

    @DisplayName("Проверка получения следующего вопроса")
    @Test
    public void checkNext() {
        Question nextQuestion = questionDao.next();
        Assertions.assertEquals(nextQuestion.getQuestion(), "Сколько примитивных типов в java?");
        Assertions.assertEquals(nextQuestion.getAnswer(), "8");
    }

    @DisplayName("Проверка получения текущего вопроса")
    @Test
    public void checkCurrent() {
        Question currentQuestion = questionDao.current();
        Assertions.assertEquals(currentQuestion.getQuestion(), "Сколько примитивных типов в java?");
        Assertions.assertEquals(currentQuestion.getAnswer(), "8");
    }
}