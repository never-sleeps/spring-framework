package service;

import dao.QuestionDao;
import dao.GameDao;

public class GameServiceImpl implements GameService {

    private QuestionDao questionDao;
    private GameDao gameDao;

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

    @Override
    public void startGame(String name, String surname) {
        gameDao.getGameForStudent(name, surname);
    }

    @Override
    public int questionCount() {
        return questionDao.getCount();
    }
}
