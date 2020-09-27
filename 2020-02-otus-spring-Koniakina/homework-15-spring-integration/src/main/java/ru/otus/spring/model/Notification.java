package ru.otus.spring.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.function.Function;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    private String email;
    private String text;

    public static final Function<Notification, String> NOTIFICATION_STRING_FUNCTION =
            notification -> String.format("%s: %s", notification.getEmail(), notification.getText());
}
