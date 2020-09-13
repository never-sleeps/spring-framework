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

import java.util.List;

@ChangeLog(order = "001")
@AllArgsConstructor
public class DatabaseChangelog {

    @ChangeSet(order = "001", id = "drop DB", author = "k-irina-alexandrovna")
    public void dropDB(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", id = "init DB", author = "k-irina-alexandrovna")
    public void initDB(MongoTemplate mongoTemplate) {

        Author IlyaIlf = mongoTemplate.save(new Author("Илья Ильф"));
        Author EvgenyPetrov = mongoTemplate.save(new Author("Евгений Петров"));
        Author EricFreeman = mongoTemplate.save(new Author("Эрик Фримен"));
        Author ElizabethFreeman = mongoTemplate.save(new Author("Элизабет Фримен"));
        Author StephenHawking = mongoTemplate.save(new Author("Стивен Хокинг"));
        Author RobertMartin = mongoTemplate.save(new Author("Роберт Мартин"));

        Genre romance = mongoTemplate.save(new Genre("Роман"));
        Genre adventure = mongoTemplate.save(new Genre("Приключения"));
        Genre comedy = mongoTemplate.save(new Genre("Комедия"));
        Genre educational = mongoTemplate.save(new Genre("Учебная литература"));
        Genre forKids = mongoTemplate.save(new Genre("Детская литература"));

        Book theTwelveChairs = mongoTemplate.save(new Book("Двенадцать стульев",
                List.of(IlyaIlf, EvgenyPetrov), List.of(adventure, comedy)));
        Book designPatterns = mongoTemplate.save(new Book("Паттерны проектирования",
                List.of(EricFreeman, ElizabethFreeman), List.of(educational)));
        Book GeorgeAndTheSecretsOfTheUniverse = mongoTemplate.save(new Book("Джордж и тайны Вселенной",
                List.of(StephenHawking), List.of(adventure, educational, forKids)));

        mongoTemplate.save(new Comment("Полезно для програмиста", designPatterns));
        mongoTemplate.save(new Comment("Про космос", GeorgeAndTheSecretsOfTheUniverse));
        mongoTemplate.save(new Comment("Для детей от 7 до 11 лет", GeorgeAndTheSecretsOfTheUniverse));
    }

}
