package ru.otus.spring.shell;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.CommandNotCurrentlyAvailable;
import org.springframework.shell.Shell;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DisplayName("Класс команд shell ")
@SpringBootTest
class ShellCommandsTest {

    @Autowired
    private Shell shell;

    private static final String COMMAND_LOGIN_1 = "login";
    private static final String COMMAND_LOGIN_2 = "name";
    private static final String COMMAND_LOGIN_3 = "I am";

    private static final String DEFAULT_LOGIN = "Student";
    private static final String CUSTOM_LOGIN = "Иван";
    private static final String GREETING_PATTERN = "Добро пожаловать, %s";
    private static final String COMMAND_LOGIN_PATTERN = "%s %s";

    private static final String COMMAND_START_GAME = "start";

    @DisplayName("Проверка команды авторизации: login")
    @Test
    void shouldReturnExpectedGreetingAfterCommandLogin1() {
        String res = (String) shell.evaluate(() -> COMMAND_LOGIN_1);
        assertThat(res).isEqualTo(String.format(GREETING_PATTERN, DEFAULT_LOGIN));
    }

    @DisplayName("Проверка команды авторизации: name")
    @Test
    void shouldReturnExpectedGreetingAfterCommandLogin2() {
        String res = (String) shell.evaluate(() -> COMMAND_LOGIN_2);
        assertThat(res).isEqualTo(String.format(GREETING_PATTERN, DEFAULT_LOGIN));
    }

    @DisplayName("Проверка команды авторизации: I am")
    @Test
    void shouldReturnExpectedGreetingAfterCommandLogin3() {
        String res = (String) shell.evaluate(() -> COMMAND_LOGIN_3);
        assertThat(res).isEqualTo(String.format(GREETING_PATTERN, DEFAULT_LOGIN));
    }

    @DisplayName("Проверка авторизации с введением пользовательского значения")
    @Test
    void shouldReturnExpectedGreetingAfterCommandCustomLogin() {
        String res = (String) shell.evaluate(() -> String.format(COMMAND_LOGIN_PATTERN, COMMAND_LOGIN_1, CUSTOM_LOGIN));
        assertThat(res).isEqualTo(String.format(GREETING_PATTERN, CUSTOM_LOGIN));
    }

    @DisplayName("Проверка отсутствия возможности запуска игры без авторизации")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    void shouldReturnCommandNotCurrentlyAvailableObjectWhenUserDoesNotLogin() {
        Object res =  shell.evaluate(() -> COMMAND_START_GAME);
        assertThat(res).isInstanceOf(CommandNotCurrentlyAvailable.class);
    }
}