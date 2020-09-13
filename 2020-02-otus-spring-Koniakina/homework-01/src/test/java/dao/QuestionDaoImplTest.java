package dao;

import data.CSVLoaderImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QuestionDaoImplTest {

    QuestionDao questionDao = new QuestionDaoImpl(new CSVLoaderImpl(), "/questions.csv");

    @Test
    void checkNext() {
        assertEquals(questionDao.next().getQuestion(), "How many primitive types are there in java?");
    }

    @Test
    void checkCurrent() {
        questionDao.next();
        assertEquals(questionDao.current().getQuestion(), "How many primitive types are there in java?");
    }

    @Test
    void checkGetCount() {
        assertEquals(questionDao.getCount(), 5);
    }
}