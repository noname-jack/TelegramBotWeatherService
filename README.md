
# Spring Telegram Bot для просмотра погоды

Spring Telegram Bot для просмотра прогноза погоды, маршрутизации запросов идёт в микросервис [GismeteoApiService](https://github.com/noname-jack/GismeteoApiService).

## Используемые технологии
- Spring Boot 3.3.1
- Spring Data JPA
- Lombok
- Liquibase
- OpenFeign
- PostgreSQL

## Использование
1. В `application.yaml` установите личный токен для подключения к Telegram боту:
    ```yaml
    bot:
      token: YOUR_TELEGRAM_BOT_TOKEN
      name: YOUR_TELEGRAM_BOT_NAME
    ```
2. Установите значения для подключения к базе данных:
    ```yaml
    spring:
      datasource:
        url: YOUR_DATABASE_URL
        username: YOUR_DATABASE_USERNAME
        password: YOUR_DATABASE_PASSWORD
        driver-class-name: YOUR_DATABASE_DRIVER_CLASS_NAME
    ```
