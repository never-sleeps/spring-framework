package dao;

import entity.Game;

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
