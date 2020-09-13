package dao;

import data.CSVLoader;
import data.CSVLoaderImpl;
import entity.Question;

import java.util.List;
import java.util.ListIterator;

public class QuestionDaoImpl implements QuestionDao {

    private List<Question> questions;
    private Question current;
    private ListIterator<Question> listIterator;

    public QuestionDaoImpl(CSVLoader loader, String fileName) {
        questions = loader.load(CSVLoaderImpl.class.getResource(fileName).getPath());
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
