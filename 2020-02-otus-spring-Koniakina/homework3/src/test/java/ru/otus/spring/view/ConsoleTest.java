package ru.otus.spring.view;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = Console.class)
class ConsoleTest {

    Console console = new Console();

    @Test
    void read() {
        String data = "hello";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        assertEquals(console.read(), "hello");
    }

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