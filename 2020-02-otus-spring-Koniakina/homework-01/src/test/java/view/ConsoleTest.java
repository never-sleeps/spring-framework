package view;

import org.junit.jupiter.api.Test;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConsoleTest {

    Console console = new view.Console();


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
        assertEquals("hello", outContent.toString());

        System.setOut(originalOut);
    }

}