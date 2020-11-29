package ru.otus.spring.util;

import java.util.Random;

public class Util {
    public static void sleepRandomly() {
        boolean isDemo = new Random().nextBoolean();
        if(isDemo) {
            System.out.println("It is a chance for demonstrating Hystrix action");
            try {
                System.out.println("Start sleeping...." + System.currentTimeMillis());
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                System.out.println("Hystrix thread interupted...." + System.currentTimeMillis());
                e.printStackTrace();
            }
        }
    }
}
