package ru.otus.spring.entity;

public class Game {
    private String name;
    private int score;

    public Game(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void increaseScore() {
        this.score++;
    }
}
