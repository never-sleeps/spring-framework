package ru.otus.spring.config;

import com.github.cloudyrock.mongock.SpringMongock;
import com.github.cloudyrock.mongock.SpringMongockBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.spring.config.migration.DatabaseChangelog;


@Configuration
public class MongoConfig {

    @Bean
    public SpringMongock mongock(MongoTemplate mongoTemplate) {
        return new SpringMongockBuilder(mongoTemplate, DatabaseChangelog.class.getPackage().getName())
                .setLockQuickConfig()
                .build();
    }
}
