import org.springframework.context.support.ClassPathXmlApplicationContext;
import service.GameService;
import view.Console;


import java.util.stream.IntStream;

public class SimpleSpringApplication implements SpringApplication {

    public void start(){

        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("/spring-context.xml");
        GameService service = context.getBean(GameService.class);
        Console console = context.getBean(Console.class);

        console.write("Давай поиграем!\nВведи своё имя:");
        String name = console.read();
        console.write("Введи свою фамилию");
        String surname = console.read();

        service.startGame(name, surname);

        IntStream.range(0, service.questionCount()).forEach(i ->{
            console.write(service.getQuestion());
            if(!service.checkAnswerIsRight(console.read())) {
                console.write(String.format("Правильный ответ: %s", service.getAnswer()));
            }
        });

        console.write(service.isWin()?"Молодец!":"Увы, были ошибки");
    }
}
