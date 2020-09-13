package ru.otus.spring.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.data.builder.MongoItemReaderBuilder;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.spring.model.jpa.AuthorJpa;
import ru.otus.spring.model.jpa.BookJpa;
import ru.otus.spring.model.jpa.CommentJpa;
import ru.otus.spring.model.jpa.GenreJpa;
import ru.otus.spring.model.mongo.AuthorMongo;
import ru.otus.spring.model.mongo.BookMongo;
import ru.otus.spring.model.mongo.CommentMongo;
import ru.otus.spring.model.mongo.GenreMongo;

import javax.persistence.EntityManagerFactory;
import java.util.HashMap;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class StepConfig {

    private final StepBuilderFactory stepBuilderFactory;
    private final MongoTemplate mongoTemplate;
    private final EntityManagerFactory emf;
    private final StatisticsListener statisticsListener;

    private final int CHUNK_SIZE = 5;

    private <T> MongoItemReader<T> reader(Class<T> clazz) {
        return new MongoItemReaderBuilder<T>()
                .name("reader")
                .template(mongoTemplate)
                .jsonQuery("{}")
                .targetType(clazz)
                .sorts((new HashMap<>()))
                .build()
                ;
    }

    private  <T> JpaItemWriter<T> writer() {
        return new JpaItemWriterBuilder<T>()
                .entityManagerFactory(emf)
                .build()
                ;
    }

    @Bean
    public Step stepGenre() {
        return createStep(
                "stepGenre",
                reader(GenreMongo.class),
                GenreJpa::new,
                "Жанры:"
        );
    }

    @Bean
    public Step stepAuthor() {
        return createStep(
                "stepAuthor",
                reader(AuthorMongo.class),
                AuthorJpa::new,
                "Авторы:"
        );
    }

    @Bean
    public Step stepBook() {
        return createStep(
                "stepBook",
                reader(BookMongo.class),
                BookJpa::new,
                "Книги:"
        );
    }

    @Bean
    public Step stepComment() {
        return createStep(
                "stepComment",
                reader(CommentMongo.class),
                CommentJpa::new,
                "Комментарии:"
                );
    }

    private <I, O> Step createStep(
            String stepName,
            ItemStreamReader<I> reader,
            ItemProcessor<I, O> itemProcessor,
            String objectType
    ) {
        return stepBuilderFactory
                .get(stepName)
                .<I, O>chunk(CHUNK_SIZE)
                .reader(reader)
                .processor(itemProcessor)
                .writer(writer())
                .allowStartIfComplete(true)
                .listener(Listeners.createItemReadListener(log))
                .listener(Listeners.createItemWriteListener(log))
                .listener(Listeners.createItemProcessListener(log))
                .listener(Listeners.createChunkListener(log))
                .listener(statisticsListener.getStepExecutionListener(objectType))
                .build();
    }

}
