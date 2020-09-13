package service;

import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GameServiceImplTest {

    @Test
    void checkGameForAllAnswersRight(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");

        GameService service = context.getBean(GameService.class);
        service.startGame("Irina", "Koniakina");

        String[] answers = new String[]{"8", "127", "Object", "null", "false"};
        for (String answer: answers) {
            service.getQuestion();
            service.checkAnswerIsRight(answer);
        }
        assertTrue(service.isWin());
    }

    @Test
    void checkGameForAllAnswersWrong(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");

        GameService service = context.getBean(GameService.class);
        service.startGame("Irina", "Koniakina");

        String[] answers = new String[]{"5", "8", "Main", "false", "true"};
        for (String answer: answers) {
            service.getQuestion();
            service.checkAnswerIsRight(answer);
        }
        assertFalse(service.isWin());
    }
}