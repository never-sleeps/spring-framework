package view;

import java.util.Scanner;

public class Console{

    public String read() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public void write(String message) {
        System.out.println(message);
    }
}
