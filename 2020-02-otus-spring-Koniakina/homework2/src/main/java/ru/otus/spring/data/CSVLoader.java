package ru.otus.spring.data;

import ru.otus.spring.entity.Question;

import java.util.List;

public interface CSVLoader {
    List<Question> load(String path);
}
