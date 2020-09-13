package ru.otus.spring.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QuestionTest {

    final Question question = new Question("Question text", "Answer text");

    @Test
    void checkGetQuestion(){
        assertEquals(question.getQuestion(), "Question text");
    }

    @Test
    void checkGetAnswer(){
        assertEquals(question.getAnswer(), "Answer text");
    }
}
