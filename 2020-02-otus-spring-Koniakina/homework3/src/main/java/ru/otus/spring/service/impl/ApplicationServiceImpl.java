package ru.otus.spring.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.spring.logging.Logger;
import ru.otus.spring.service.ApplicationService;
import ru.otus.spring.service.GameService;
import ru.otus.spring.view.Console;


import java.util.stream.IntStream;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    private final GameService gameService;
    private final Console console;
    private final MessageServiceImpl ms;


    public ApplicationServiceImpl(GameService gameService, Console console, MessageServiceImpl ms) {
        this.gameService = gameService;
        this.console = console;
        this.ms = ms;
    }

    @Logger
    public void start(){

        console.write(ms.getMessage("application.start"));
        console.write(ms.getMessage("user.name"));
        gameService.startGame(console.read());

        IntStream.range(0, gameService.questionCount()).forEach(i ->{
            console.write(gameService.getQuestion());
            if(!gameService.checkAnswerIsRight(console.read())) {
                console.write(ms.getMessage("application.right.answer") + ": " + gameService.getAnswer());
            }
        });

        console.write(gameService.isWin()?
                ms.getMessage("application.win") : ms.getMessage("application.lose"));
    }
}
