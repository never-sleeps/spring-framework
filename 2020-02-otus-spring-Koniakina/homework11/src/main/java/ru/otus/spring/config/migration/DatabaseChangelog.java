package ru.otus.spring.config.migration;


import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.spring.model.Author;
import ru.otus.spring.model.Book;
import ru.otus.spring.model.Comment;
import ru.otus.spring.model.Genre;

@ChangeLog(order = "001")
@AllArgsConstructor
public class DatabaseChangelog {

    @ChangeSet(order = "001", id = "drop DB", author = "k-irina-alexandrovna")
    public void dropDB(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", id = "init DB", author = "k-irina-alexandrovna")
    public void initDB(MongoTemplate mongoTemplate) {

        Author pushkin = mongoTemplate.save(new Author("Александр Сергеевич Пушкин"));
        Author lermontov = mongoTemplate.save(new Author("Михаил Юрьевич Лермонтов"));
        Author EricFreeman = mongoTemplate.save(new Author("Эрик Фримен"));
        Author StephenHawking = mongoTemplate.save(new Author("Стивен Хокинг"));

        Genre romance = mongoTemplate.save(new Genre("Роман"));
        Genre fairytale = mongoTemplate.save(new Genre("Сказка"));
        Genre adventure = mongoTemplate.save(new Genre("Приключения"));
        Genre educational = mongoTemplate.save(new Genre("Учебная литература"));

        Comment comment1 = new Comment("Полезно для програмиста");
        Comment comment2 = new Comment("Про космос");

        Book goldFish = mongoTemplate.save(new Book("Золотая рыбка",
                pushkin,
                fairytale));
        Book designPatterns = mongoTemplate.save(new Book("Паттерны проектирования",
                EricFreeman,
                educational,
                comment1));
        Book GeorgeAndTheSecretsOfTheUniverse = mongoTemplate.save(new Book("Джордж и тайны Вселенной",
                StephenHawking,
                adventure,
                comment2));
    }

}
