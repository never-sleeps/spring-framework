package ru.otus.spring.data;

import ru.otus.spring.entity.Question;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class CSVLoaderImplTest {

    @Test
    void checkLoad() {
        String filePath = String.format("/questions_%s.csv", Locale.getDefault().toString());
        CSVLoader csvLoader = new CSVLoaderImpl();
        List<Question> questions = csvLoader.load(CSVLoaderImplTest.class.getResource(filePath).getPath());
        assertNotNull(questions);
    }
}