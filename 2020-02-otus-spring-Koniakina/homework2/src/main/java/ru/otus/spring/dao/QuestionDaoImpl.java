package ru.otus.spring.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;
import ru.otus.spring.data.CSVLoader;
import ru.otus.spring.data.CSVLoaderImpl;
import ru.otus.spring.entity.Question;

import java.util.List;
import java.util.ListIterator;
import java.util.Locale;

@Repository("questionDao")
@PropertySource({"classpath:application.properties"})
public class QuestionDaoImpl implements QuestionDao {

    private List<Question> questions;
    private Question current;
    private ListIterator<Question> listIterator;

    public QuestionDaoImpl(CSVLoader loader, @Value("${questions.file}") String fileName) {
        String filePath = String.format("/%s_%s.csv", fileName, Locale.getDefault().toString());
        questions = loader.load(CSVLoaderImpl.class.getResource(filePath).getPath());
        listIterator = questions.listIterator();
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
}
