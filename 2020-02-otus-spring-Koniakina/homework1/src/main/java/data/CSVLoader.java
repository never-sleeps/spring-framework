package data;

import entity.Question;

import java.util.List;

public interface CSVLoader {
    List<Question> load(String path);
}
