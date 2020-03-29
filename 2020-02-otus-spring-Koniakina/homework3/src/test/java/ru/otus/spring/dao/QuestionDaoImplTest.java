package ru.otus.spring.dao;

import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.spring.config.ApplicationProperties;
import ru.otus.spring.dao.impl.QuestionDaoImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QuestionDaoImplTest {

    @MockBean
    private ApplicationProperties applicationProperties;
    QuestionDao questionDao = new QuestionDaoImpl(applicationProperties);

    @Test
    void checkNext() {
        Assertions.assertEquals(questionDao.next().getQuestion(), "Сколько примитивных типов в java?");
    }

    @Test
    void checkCurrent() {
        questionDao.next();
        Assertions.assertEquals(questionDao.current().getQuestion(), "Сколько примитивных типов в java?");
    }

    @Test
    void checkGetCount() {
        assertEquals(questionDao.getCount(), 5);
    }
}