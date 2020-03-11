package ru.otus.spring.dao;

import org.springframework.stereotype.Service;
import ru.otus.spring.entity.Game;

@Service("gameDao")
public class GameDaoImpl implements GameDao {

    private Game current;

    @Override
    public Game getGameForStudent(String name, String surname) {
        if(current == null) {
            current = new Game(name, surname);
        }
        return current;
    }

    @Override
    public void increaseScore() {
        current.increaseScore();
    }

    @Override
    public int getScore() {
        return current.getScore();
    }
}
