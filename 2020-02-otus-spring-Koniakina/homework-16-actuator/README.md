## Spring Framework
### Тема: Spring Boot Actuator, HATEOAS подход, Spring Data REST
#### Домашнее задание №16:
**Использовать метрики, healthchecks и logfile**

Цель: реализовать production-grade мониторинг и прозрачность в приложении

Результат: приложение с применением Spring Boot Actuator

1. Подключить Spring Boot Actuator в приложение.
2. Включить метрики, healthchecks и logfile.
3. Реализовать свой собственный HealthCheck индикатор
4. UI для данных от Spring Boot Actuator реализовывать не нужно.
5. Опционально: переписать приложение на HATEOAS принципах с помощью Spring Data REST Repository

The HAL Browser (for Spring Data REST): http://localhost:8080/rest/api/browser/index.html#/
Actuator: http://localhost:8080/actuator