package ru.otus.spring.view;

import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class Console{

    public String read() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public void write(String message) {
        System.out.println(message);
    }
}
