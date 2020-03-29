package ru.otus.spring.dao.impl;

import org.springframework.stereotype.Service;
import ru.otus.spring.dao.GameDao;
import ru.otus.spring.entity.Game;

@Service("gameDao")
public class GameDaoImpl implements GameDao {

    private Game current;

    @Override
    public Game getGameForStudent(String name) {
        if(current == null) {
            current = new Game(name);
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
