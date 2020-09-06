package ru.otus.spring.batch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableBatchProcessing
@RequiredArgsConstructor
@Configuration
public class JobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final Step stepGenre;
    private final Step stepAuthor;
    private final Step stepBook;
    private final Step stepComment;

    private final String JOB_NAME = "migrationJob";

    @Bean
    public Job migrationJob() {
        return jobBuilderFactory
                .get(JOB_NAME)
                .incrementer(new RunIdIncrementer())
                .flow(stepAuthor)
                .next(stepGenre)
                .next(stepBook)
                .next(stepComment)
                .end()
                .build()
                ;
    }
}
