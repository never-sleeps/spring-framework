package ru.otus.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.service.ApplicationService;
import ru.otus.spring.service.GameService;
import ru.otus.spring.service.MessageService;

@ShellComponent
@RequiredArgsConstructor
public class ShellCommands {
    private final ApplicationService applicationService;
    private final GameService gameService;
    private final MessageService messageService;
    private String userName;

    @ShellMethod(value = "Login command", key = {"login", "name", "I am"})
    public String login(@ShellOption(defaultValue = "Student") String userName) {
        this.userName = userName;
        gameService.startGame(userName);
        return String.format("%s, %s", messageService.getMessage("application.start"), userName);
    }

    @ShellMethod(value = "Start game", key = {"start", "game", "play"})
    @ShellMethodAvailability(value = "isStartGameCommandAvailable")
    public String startGame() {
        applicationService.start();
        return messageService.getMessage("application.end");
    }

    private Availability isStartGameCommandAvailable() {
        return userName != null ? Availability.available()
                : Availability.unavailable(messageService.getMessage("message.empty.full.name"));
    }
}
