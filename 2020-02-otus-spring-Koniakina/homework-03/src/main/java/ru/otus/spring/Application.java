package ru.otus.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import ru.otus.spring.config.ApplicationProperties;
import ru.otus.spring.service.ApplicationService;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationProperties.class)
public class Application {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);

        ApplicationService applicationService = context.getBean(ApplicationService.class);
        applicationService.start();

        context.close();
    }
}
