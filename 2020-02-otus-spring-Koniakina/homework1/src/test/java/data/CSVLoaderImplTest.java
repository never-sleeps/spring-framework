package data;

import entity.Question;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CSVLoaderImplTest {

    @Test
    void checkLoad() {
        CSVLoader csvLoader = new CSVLoaderImpl();
        List<Question> questions = csvLoader.load(CSVLoaderImplTest.class.getResource("/questions.csv").getPath());
        assertNotNull(questions);
    }
}