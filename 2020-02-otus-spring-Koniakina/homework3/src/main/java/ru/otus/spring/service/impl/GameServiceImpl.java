package ru.otus.spring.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.spring.dao.GameDao;
import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.aspects.logging.Logger;
import ru.otus.spring.service.GameService;

@Service
public class GameServiceImpl implements GameService {

    private final QuestionDao questionDao;
    private final GameDao gameDao;

    public GameServiceImpl(QuestionDao questionDao, GameDao gameDao) {
        this.questionDao = questionDao;
        this.gameDao = gameDao;
    }

    @Override
    public String getQuestion() {
        return questionDao.next().getQuestion();
    }

    @Override
    public boolean checkAnswerIsRight(String answer) {
        if(questionDao.current().getAnswer().equals(answer)){
            gameDao.increaseScore();
            return true;
        }
        return false;
    }

    @Override
    public String getAnswer() {
        return questionDao.current().getAnswer();
    }

    @Override
    public boolean isWin() {
        return gameDao.getScore() == questionDao.getCount();
    }

    @Logger
    @Override
    public void startGame(String name) {
        gameDao.getGameForStudent(name);
    }

    @Override
    public int questionCount() {
        return questionDao.getCount();
    }
}
