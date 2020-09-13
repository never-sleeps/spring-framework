package ru.otus.spring.config.changelogs;


import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.spring.model.mongo.AuthorMongo;
import ru.otus.spring.model.mongo.BookMongo;
import ru.otus.spring.model.mongo.CommentMongo;
import ru.otus.spring.model.mongo.GenreMongo;

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

        AuthorMongo IlyaIlf = mongoTemplate.save(new AuthorMongo("Илья Ильф"));
        AuthorMongo EvgenyPetrov = mongoTemplate.save(new AuthorMongo("Евгений Петров"));
        AuthorMongo EricFreeman = mongoTemplate.save(new AuthorMongo("Эрик Фримен"));
        AuthorMongo ElizabethFreeman = mongoTemplate.save(new AuthorMongo("Элизабет Фримен"));
        AuthorMongo StephenHawking = mongoTemplate.save(new AuthorMongo("Стивен Хокинг"));
        AuthorMongo RobertMartin = mongoTemplate.save(new AuthorMongo("Роберт Мартин"));

        GenreMongo romance = mongoTemplate.save(new GenreMongo("Роман"));
        GenreMongo adventure = mongoTemplate.save(new GenreMongo("Приключения"));
        GenreMongo comedy = mongoTemplate.save(new GenreMongo("Комедия"));
        GenreMongo educational = mongoTemplate.save(new GenreMongo("Учебная литература"));
        GenreMongo forKids = mongoTemplate.save(new GenreMongo("Детская литература"));

        BookMongo theTwelveChairs = mongoTemplate.save(
                new BookMongo("Двенадцать стульев", List.of(IlyaIlf, EvgenyPetrov), List.of(adventure, comedy))
        );
        BookMongo designPatterns = mongoTemplate.save(
                new BookMongo("Паттерны проектирования", List.of(EricFreeman, ElizabethFreeman), List.of(educational))
        );
        BookMongo GeorgeAndTheSecretsOfTheUniverse = mongoTemplate.save(
                new BookMongo("Джордж и тайны Вселенной", List.of(StephenHawking), List.of(adventure, educational, forKids))
        );

        mongoTemplate.save(new CommentMongo("Полезно для програмиста", designPatterns));
        mongoTemplate.save(new CommentMongo("Про космос", GeorgeAndTheSecretsOfTheUniverse));
        mongoTemplate.save(new CommentMongo("Для детей от 7 до 11 лет", GeorgeAndTheSecretsOfTheUniverse));
    }

}
