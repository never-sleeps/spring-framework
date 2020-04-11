package ru.otus.spring.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import ru.otus.spring.aspects.logging.Logger;

import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

@ConfigurationProperties(prefix = "application")
@Slf4j
@Data
public class ApplicationProperties {

    private Locale locale;
    private String csvFileName;
    private Map<String, String> languages;

    private final Locale defaultLocale = Locale.ENGLISH;

    public Locale getLanguageLocale() {
        if(locale != null) {
            return locale;
        }
        return defaultLocale;
    }

    public String getCsvFile() {
        for (Entry<String, String> entry : languages.entrySet()) {
            String code = entry.getKey();
            if (locale != null && locale.toString().equals(code)) {
                return csvFileName.replace(".csv", String.format("_%s.csv", code));
            }
        }
        return csvFileName.replace(".csv", "_en_US.csv");
    }
}
