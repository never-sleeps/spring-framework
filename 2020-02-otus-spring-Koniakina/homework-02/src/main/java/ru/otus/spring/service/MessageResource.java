package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;
import ru.otus.spring.view.Console;

import java.util.Locale;

@Service
@PropertySource("classpath:application.properties")
public class MessageResource implements Localization{

    private final MessageSource ms;
    private Locale locale;

    public MessageResource(@Value("${bundle.directory}") String name,
                           @Value("${file.encoding}") String encoding) {
        ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
        ms.setBasename("classpath:" + name);
        ms.setDefaultEncoding(encoding);
        this.ms = ms;
        this.locale = Locale.forLanguageTag(Locale.getDefault().toString());
    }

    public String getL10n(String value) {
        return ms.getMessage(value, null, this.locale);
    }

    public void changeLocale(String languageTag){
        switch (languageTag.toLowerCase()){
            case "ru":
                this.locale = Locale.forLanguageTag("ru-RU"); break;
            case "en":
                this.locale = Locale.ENGLISH; break;
            default:
                System.out.println(getL10n("application.default.lang"));
        }
    }
}
