package config.migration;


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
    public void init(MongoTemplate mongoTemplate) {
        Author IlyaIlf = mongoTemplate.save(new Author("1","Илья Ильф"));
        Author EvgenyPetrov = mongoTemplate.save(new Author("2","Евгений Петров"));
        Author EricFreeman = mongoTemplate.save(new Author("3","Эрик Фримен"));
        Author ElizabethFreeman = mongoTemplate.save(new Author("4","Элизабет Фримен"));
        Author StephenHawking = mongoTemplate.save(new Author("5","Стивен Хокинг"));
        Author RobertMartin = mongoTemplate.save(new Author("6","Роберт Мартин"));

        Genre romance = mongoTemplate.save(new Genre("1", "Роман"));
        Genre adventure = mongoTemplate.save(new Genre("2", "Приключения"));
        Genre comedy = mongoTemplate.save(new Genre("3", "Комедия"));
        Genre educational = mongoTemplate.save(new Genre("4", "Учебная литература"));
        Genre forKids = mongoTemplate.save(new Genre("5", "Детская литература"));
        Genre detective = mongoTemplate.save(new Genre("6", "Детектив"));

        Book theTwelveChairs = mongoTemplate.save(new Book("1", "Двенадцать стульев",
                List.of(IlyaIlf, EvgenyPetrov), List.of(adventure, comedy)));
        Book designPatterns = mongoTemplate.save(new Book("2", "Паттерны проектирования",
                List.of(EricFreeman, ElizabethFreeman), List.of(educational)));
        Book GeorgeAndTheSecretsOfTheUniverse = mongoTemplate.save(new Book("3", "Джордж и тайны Вселенной",
                List.of(StephenHawking), List.of(adventure, educational, forKids)));

        mongoTemplate.save(new Comment("0","Полезно для програмиста", designPatterns));
        mongoTemplate.save(new Comment("1","Про космос", GeorgeAndTheSecretsOfTheUniverse));
        mongoTemplate.save(new Comment("2","Для детей от 7 до 11 лет", GeorgeAndTheSecretsOfTheUniverse));
    }
}
