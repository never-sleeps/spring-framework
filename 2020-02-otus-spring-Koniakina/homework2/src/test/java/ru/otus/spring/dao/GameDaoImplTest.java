package ru.otus.spring.dao;

import org.junit.jupiter.api.Assertions;
import ru.otus.spring.entity.Game;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GameDaoImplTest {

    GameDao gameDao = new GameDaoImpl();

    @Test
    void checkGetGameForStudent() {
        Game game = gameDao.getGameForStudent("Irina" , "Koniakina");
        Assertions.assertEquals(game, gameDao.getGameForStudent("Irina", "Koniakina"));
    }

    @Test
    void checkIncreaseScore() {
        Game game = gameDao.getGameForStudent("Irina" , "Koniakina");
        game.increaseScore();
        assertEquals(game.getScore(), 1);
    }

    @Test
    void checkGetScore() {
        Game game = gameDao.getGameForStudent("Irina" , "Koniakina");
        assertEquals(game.getScore(), 0);
    }
}