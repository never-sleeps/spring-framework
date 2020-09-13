package ru.otus.spring.application;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.otus.spring.service.GameService;
import ru.otus.spring.service.MessageResource;
import ru.otus.spring.view.Console;


import java.util.stream.IntStream;

@Configuration
@ComponentScan(basePackages = "ru.otus.spring")
public class SimpleSpringApplication implements SpringApplication {

    public void start(){

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(SimpleSpringApplication.class);
        GameService service = context.getBean(GameService.class);
        Console console = context.getBean(Console.class);
        MessageResource ms = context.getBean(MessageResource.class);


        console.write(ms.getL10n("application.lang"));
        ms.changeLocale(console.read());

        console.write(ms.getL10n("application.start"));
        console.write(ms.getL10n("user.name"));
        String name = console.read();
        console.write(ms.getL10n("user.surname"));
        String surname = console.read();

        service.startGame(name, surname);

        IntStream.range(0, service.questionCount()).forEach(i ->{
            console.write(service.getQuestion());
            if(!service.checkAnswerIsRight(console.read())) {
                console.write(ms.getL10n("application.right.answer") + ": " + service.getAnswer());
            }
        });

        console.write(service.isWin()?
                ms.getL10n("application.win") : ms.getL10n("application.lose"));
    }
}
