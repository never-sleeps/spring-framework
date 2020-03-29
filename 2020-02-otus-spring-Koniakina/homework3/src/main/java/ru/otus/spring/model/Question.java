package ru.otus.spring.model;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;

@Data
public class Question {
    @CsvBindByPosition(position = 0)
    private String question;
    @CsvBindByPosition(position = 1)
    private String answer;
}
