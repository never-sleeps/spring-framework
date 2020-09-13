package config.changelogs;


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
    public void init(MongoTemplate mongoTemplate) {
        AuthorMongo IlyaIlf = mongoTemplate.save(new AuthorMongo("1","Илья Ильф"));
        AuthorMongo EvgenyPetrov = mongoTemplate.save(new AuthorMongo("2","Евгений Петров"));
        AuthorMongo EricFreeman = mongoTemplate.save(new AuthorMongo("3","Эрик Фримен"));
        AuthorMongo ElizabethFreeman = mongoTemplate.save(new AuthorMongo("4","Элизабет Фримен"));
        AuthorMongo StephenHawking = mongoTemplate.save(new AuthorMongo("5","Стивен Хокинг"));
        AuthorMongo RobertMartin = mongoTemplate.save(new AuthorMongo("6","Роберт Мартин"));

        GenreMongo romance = mongoTemplate.save(new GenreMongo("1", "Роман"));
        GenreMongo adventure = mongoTemplate.save(new GenreMongo("2", "Приключения"));
        GenreMongo comedy = mongoTemplate.save(new GenreMongo("3", "Комедия"));
        GenreMongo educational = mongoTemplate.save(new GenreMongo("4", "Учебная литература"));
        GenreMongo forKids = mongoTemplate.save(new GenreMongo("5", "Детская литература"));
        GenreMongo detective = mongoTemplate.save(new GenreMongo("6", "Детектив"));

        BookMongo theTwelveChairs = mongoTemplate.save(new BookMongo("1", "Двенадцать стульев",
                List.of(IlyaIlf, EvgenyPetrov), List.of(adventure, comedy)));
        BookMongo designPatterns = mongoTemplate.save(new BookMongo("2", "Паттерны проектирования",
                List.of(EricFreeman, ElizabethFreeman), List.of(educational)));
        BookMongo GeorgeAndTheSecretsOfTheUniverse = mongoTemplate.save(new BookMongo("3", "Джордж и тайны Вселенной",
                List.of(StephenHawking), List.of(adventure, educational, forKids)));

        mongoTemplate.save(new CommentMongo("0","Полезно для програмиста", designPatterns));
        mongoTemplate.save(new CommentMongo("1","Про космос", GeorgeAndTheSecretsOfTheUniverse));
        mongoTemplate.save(new CommentMongo("2","Для детей от 7 до 11 лет", GeorgeAndTheSecretsOfTheUniverse));
    }
}
