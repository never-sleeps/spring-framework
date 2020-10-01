package ru.otus.spring.model.enums;

import lombok.Getter;

@Getter
public enum DeliveryType {
    LAND("наземный транспорт"),
    AIR("воздушный транспорт");

    private String name;
    DeliveryType(String name) {
        this.name = name;
    }

    public static DeliveryType getRandom() {
        return values()[(int) (Math.random() * values().length)];
    }
}
