package dao;

import entity.Game;

public interface GameDao {
    Game getGameForStudent(String name, String surname);
    void increaseScore();
    int getScore();
}
