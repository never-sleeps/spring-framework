package ru.otus.spring.dao.impl;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Repository;
import ru.otus.spring.config.ApplicationProperties;
import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.logging.Logger;
import ru.otus.spring.model.Question;

import java.io.InputStreamReader;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

import static java.nio.charset.StandardCharsets.UTF_8;

@Repository
public class QuestionDaoImpl implements QuestionDao {

    private final ApplicationProperties properties;

    private List<Question> questions;
    private Question current;
    private ListIterator<Question> listIterator;

    public QuestionDaoImpl(ApplicationProperties properties) {
        this.properties = properties;
        initQuestionsList();
    }

    @Override
    public Question next() {
        current = listIterator.next();
        return current;
    }

    @Override
    public Question current() {
        return current;
    }

    @Override
    public int getCount() {
        return questions.size();
    }

    @Logger
    public void initQuestionsList() {
        InputStreamReader isReader = new InputStreamReader(Objects.requireNonNull(
                this.getClass().getClassLoader().getResourceAsStream(properties.getCsvFile())), UTF_8);
        CsvToBean<Question> csvToBean = new CsvToBeanBuilder<Question>(isReader)
                .withType(Question.class)
                .withIgnoreLeadingWhiteSpace(true)
                .withSkipLines(1)
                .build();
        questions = csvToBean.parse();
        listIterator = questions.listIterator();
    }
}
