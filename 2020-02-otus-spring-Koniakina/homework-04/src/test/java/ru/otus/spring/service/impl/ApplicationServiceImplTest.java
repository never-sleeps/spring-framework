package ru.otus.spring.service.impl;

import org.junit.FixMethodOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.dao.impl.GameDaoImpl;
import ru.otus.spring.model.Question;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@DisplayName("Класс запуска приложения")
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ApplicationServiceImplTest {

    @Autowired
    private GameServiceImpl gameService;
    @Autowired
    private GameDaoImpl gameDao;
    @MockBean
    private QuestionDao questionDao;

    private static Question question = new Question();
    static {
        question.setQuestion("Is it a test?");
        question.setAnswer("yes");
    }

    @BeforeEach
    public void setUp(){
        gameService.startGame("Irina");

        when(questionDao.next()).thenReturn(question);
        when(questionDao.current()).thenReturn(question);
        when(questionDao.getCount()).thenReturn(1);
    }

    @DisplayName("Проверка поражения при неверном ответе")
    @Test
    public void checkStart1() {
        gameService.getQuestion();
        gameService.checkAnswerIsRight("no");
        assertFalse(gameService.isWin());
    }

    @DisplayName("Проверка победы при верном ответе")
    @Test
    public void checkStart2() {
        gameService.getQuestion();
        gameService.checkAnswerIsRight("yes");
        assertTrue(gameService.isWin());
    }

}