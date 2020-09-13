package ru.otus.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.spring.config.ApplicationProperties;
import ru.otus.spring.service.MessageService;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageSource messageSource;
    private final ApplicationProperties localProps;

    @Override
    public String getMessage(String message, Object... objects) {
        return messageSource.getMessage(message, objects, localProps.getLanguageLocale());
    }
}
