package ru.otus.spring.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import ru.otus.spring.logging.Logger;

import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

@ConfigurationProperties(prefix = "application")
@Slf4j
@Data
public class ApplicationProperties {

    private Locale locale;
    private Map<String, String> languages;
    private String csvFileName;
    private final Locale defaultLocale = Locale.ENGLISH;

    public Locale getLanguageLocale() {
        if(locale != null) {
            log.info("Locale is {}", locale);
            return locale;
        }
        log.info("Locale is empty, default locale = {}", defaultLocale);
        return defaultLocale;
    }

    public String getCsvFile() {
        for (Entry<String, String> entry : languages.entrySet()) {
            String code = entry.getKey();
            if (locale != null && locale.toString().equals(code)) {
                String currentCsvFileName = csvFileName.replace(".csv", "_" + code + ".csv");
                return currentCsvFileName;
            }
        }
        return csvFileName.replace(".csv", "_en_US.csv");
    }
}
