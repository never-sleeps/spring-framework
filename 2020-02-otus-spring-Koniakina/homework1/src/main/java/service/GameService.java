package service;

public interface GameService {
    String getQuestion();
    String getAnswer();
    boolean checkAnswerIsRight(String answer);
    boolean isWin();
    void startGame(String name, String surname);
    int questionCount();
}
