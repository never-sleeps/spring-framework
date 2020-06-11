## Otus (Spring Framework)
### Тема: WebFlux
#### Домашнее задание №11:
**Приложение на reactive stack**

Использовать WebFlux

Цель: Цель: разрабатывать Responsive и Resilent приложения на реактивном стеке Spring c помощью Spring Web Flux и Reactive Spring Data Repositories 

Результат: приложение на реактивном стеке Spring
1. Задание выполняется на основе ДЗ с MongoDB.
2. Необходимо использовать Reactive Spring Data Repositories. Использовать PostgreSQL и экспериментальную R2DBC не рекомендуется.
3. RxJava vs Project Reactor - на Ваш вкус.
4. Вместо классического Spring MVC и embedded Web-сервера использовать WebFlux.
5. Опционально: реализовать на Functional Endpoints

Рекомендации:
Старайтесь избавиться от лишних архитектурных слоёв. 
Самый простой вариант - весь flow в контроллере.