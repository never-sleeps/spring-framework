package ru.otus.spring.service;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.otus.spring.Application;

import static org.junit.jupiter.api.Assertions.*;

class GameServiceImplTest {

    @Test
    void checkGameForAllAnswersRight(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Application.class);

        GameService service = context.getBean(GameService.class);
        service.startGame("Irina");

        String[] answers = new String[]{"8", "127", "Object", "null", "false"};
        for (String answer: answers) {
            service.getQuestion();
            service.checkAnswerIsRight(answer);
        }
        assertTrue(service.isWin());
    }

    @Test
    void checkGameForAllAnswersWrong(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Application.class);

        GameService service = context.getBean(GameService.class);
        service.startGame("Irina");

        String[] answers = new String[]{"5", "8", "ru.otus.spring.Main", "false", "true"};
        for (String answer: answers) {
            service.getQuestion();
            service.checkAnswerIsRight(answer);
        }
        assertFalse(service.isWin());
    }
}