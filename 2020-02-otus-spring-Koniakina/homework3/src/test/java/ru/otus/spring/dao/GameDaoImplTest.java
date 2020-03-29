package ru.otus.spring.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import ru.otus.spring.dao.impl.GameDaoImpl;
import ru.otus.spring.entity.Game;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GameDaoImplTest {

    GameDao gameDao = new GameDaoImpl();

    @Test
    void checkGetGameForStudent() {
        Game game = gameDao.getGameForStudent("Irina");
        Assertions.assertEquals(game, gameDao.getGameForStudent("Irina"));
    }

    @Test
    void checkIncreaseScore() {
        Game game = gameDao.getGameForStudent("Irina");
        game.increaseScore();
        assertEquals(game.getScore(), 1);
    }

    @Test
    void checkGetScore() {
        Game game = gameDao.getGameForStudent("Irina");
        assertEquals(game.getScore(), 0);
    }
}