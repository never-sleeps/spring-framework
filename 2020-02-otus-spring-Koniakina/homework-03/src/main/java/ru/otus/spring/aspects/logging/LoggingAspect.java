package ru.otus.spring.aspects.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;


@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Before(value = "@annotation(ru.otus.spring.aspects.logging.Logger)")
    public void logBefore(JoinPoint joinPoint) {
        log.info("Прокси : " + joinPoint.getThis().getClass().getName());
        log.info("Класс : " + joinPoint.getTarget().getClass().getName());
        log.info("Вызов метода : " + joinPoint.getSignature().getName());
    }

    @Around("execution(* ru.otus.spring.config.ApplicationProperties.getLanguageLocale(..))")
    public Object logGettingLanguageLocale(ProceedingJoinPoint joinPoint) throws Throwable {
        Object obj = joinPoint.proceed();
        log.info("Locale = {}", obj.toString());
        return obj;
    }

    @Around("execution(* ru.otus.spring.config.ApplicationProperties.getCsvFile(..))")
    public Object logGettingCsvFile(ProceedingJoinPoint joinPoint) throws Throwable {
        Object obj = joinPoint.proceed();
        log.info("CSV-file = {}", obj.toString());
        return obj;
    }
}
