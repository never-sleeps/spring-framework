package ru.otus.spring.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.spring.dao.impl.QuestionDaoImpl;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Класс Yaml свойств")
@SpringBootTest
public class ApplicationPropertiesTest {

    @Autowired
    private QuestionDaoImpl questionDao;
    @Autowired
    private ApplicationProperties applicationProperties;

    @DisplayName("Проверка чтения локали из файла")
    @Test
    public void getLanguageLocale() {
        String yamlLocale = "ru_RU";
        assertEquals(yamlLocale, applicationProperties.getLanguageLocale().toString());
    }

    @DisplayName("Проверка чтения имени файла")
    @Test
    public void getCsvFile() {
        String yamlCsvFileName = "csv/questions_ru_RU.csv";
        assertEquals(yamlCsvFileName, applicationProperties.getCsvFile());
    }
}