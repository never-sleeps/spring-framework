package ru.otus.spring.data;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import ru.otus.spring.entity.Question;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service("loader")
public class CSVLoaderImpl implements CSVLoader {

    @Override
    public List<Question> load(String path) {
        List<Question> questions = new ArrayList<>();
        try {
            Reader in = new InputStreamReader(new FileInputStream(
                    URLDecoder.decode(path, StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(in);
            for (CSVRecord record : records) {
                questions.add(new Question(record.get(0), record.get(1)));
            }
        }catch (IOException e){
            System.exit(-1);
        }
        return questions;
    }

}
