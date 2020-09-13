package ru.otus.spring.shell;

import org.jline.utils.AttributedString;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.stereotype.Component;

@Component
public class ApplicationPromptProvider implements PromptProvider {

    private String PROMPT = "Library:>\t";

    @Override
    public AttributedString getPrompt() {
        return new AttributedString(PROMPT);
    }
}
