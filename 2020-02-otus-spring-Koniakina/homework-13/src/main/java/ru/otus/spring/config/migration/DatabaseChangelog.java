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
import ru.otus.spring.model.secuirity.User;
import ru.otus.spring.security.Role;

import java.util.Set;

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

        Comment comment1 = new Comment("Полезно для програмиста");
        Comment comment2 = new Comment("Про космос");
        Comment comment3 = new Comment("Для детей от 7 до 11 лет");

        Book theTwelveChairs = mongoTemplate.save(new Book("Двенадцать стульев",
                IlyaIlf,
                adventure));
        Book designPatterns = mongoTemplate.save(new Book("Паттерны проектирования",
                EricFreeman,
                educational,
                comment1));
        Book GeorgeAndTheSecretsOfTheUniverse = mongoTemplate.save(new Book("Джордж и тайны Вселенной",
                StephenHawking,
                adventure,
                comment2));
    }

    @ChangeSet(order = "003", id = "init users", author = "k-irina-alexandrovna")
    public void initUsers(MongoTemplate mongoTemplate) {
        User admin = new User("admin", "$2a$10$OtZK15XMKlvlx8q6UH.7i.y7JweojybShK4e/AsUJdlIG3TsMwTDe",
                Set.of(Role.ADMIN));
        User user = new User("user", "$2a$10$xVwGpunNBp.7XVgaDQRabuUAxdXkF6HfvXlMI0uAVFCuDzpKsJgt2",
                Set.of(Role.USER));

        mongoTemplate.save(admin);
        mongoTemplate.save(user);
    }

}
