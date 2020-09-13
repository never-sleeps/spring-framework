package ru.otus.spring.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.spring.dao.impl.GameDaoImpl;
import ru.otus.spring.entity.Game;


import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Класс GameDaoImplTest")
@SpringBootTest
public class GameDaoImplTest {

    GameDao gameDao = new GameDaoImpl();

    @Test
    public void checkGetGameForStudent() {
        Game game = gameDao.getGameForStudent("Irina");
        Assertions.assertEquals(game, gameDao.getGameForStudent("Irina"));
    }

    @Test
    public void checkIncreaseScore() {
        Game game = gameDao.getGameForStudent("Irina");
        game.increaseScore();
        assertEquals(game.getScore(), 1);
    }

    @Test
    public void checkGetScore() {
        Game game = gameDao.getGameForStudent("Irina");
        assertEquals(game.getScore(), 0);
    }
}