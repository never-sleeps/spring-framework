package ru.otus.spring.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.impl.QuestionDaoImpl;
import ru.otus.spring.aspects.logging.Logger;
import ru.otus.spring.service.ApplicationService;
import ru.otus.spring.service.GameService;
import ru.otus.spring.service.MessageService;
import ru.otus.spring.view.Console;

import java.util.stream.IntStream;

@Slf4j
@Service
public class ApplicationServiceImpl implements ApplicationService {

    private final MessageService messageService;
    private final Console console;
    private final GameService gameService;

    public ApplicationServiceImpl(MessageService messageService,
                                  Console console,
                                  GameService gameService) {
        this.messageService = messageService;
        this.console = console;
        this.gameService = gameService;
    }

    public void start(){
        IntStream.range(0, gameService.questionCount()).forEach(i ->{
            console.write(gameService.getQuestion());
            if(!gameService.checkAnswerIsRight(console.read())) {
                console.write(messageService.getMessage("application.right.answer") + ": " + gameService.getAnswer());
            }
        });
        console.write(gameService.isWin()?
                messageService.getMessage("application.win") : messageService.getMessage("application.lose"));
    }
}
