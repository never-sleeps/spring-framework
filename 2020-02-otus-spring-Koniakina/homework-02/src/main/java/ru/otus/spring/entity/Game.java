package ru.otus.spring.entity;

public class Game {
    private String name;
    private String surname;
    private int score;

    public Game(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public int getScore() {
        return score;
    }

    public void increaseScore() {
        this.score++;
    }
}
