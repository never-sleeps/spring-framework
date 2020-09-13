package ru.otus.spring.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.spring.service.MessageService;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Класс чтения сообщений из файлов properties")
@SpringBootTest
public class MessageServiceImplTest {

    @Autowired
    private MessageService messageService;

    @Test
    public void getMessage() {
        String expected = "Давай поиграем";
        String actual = messageService.getMessage("application.start");
        assertEquals(expected, actual);
    }
}