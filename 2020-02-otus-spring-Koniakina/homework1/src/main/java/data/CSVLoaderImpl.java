package data;

import entity.Question;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

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
