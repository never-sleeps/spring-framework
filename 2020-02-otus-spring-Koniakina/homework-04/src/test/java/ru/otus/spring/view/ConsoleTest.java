package ru.otus.spring.view;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("Класс работы с консолью")
@SpringBootTest
class ConsoleTest {

    Console console = new Console();

    @DisplayName("Проверка чтения из консоли")
    @Test
    void read() {
        String data = "hello";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        assertEquals(console.read(), "hello");
    }

    @DisplayName("Проверка записи в консоль")
    @Test
    void write() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        console.write("hello");
        assertEquals("hello\r\n", outContent.toString());

        System.setOut(originalOut);
    }

}