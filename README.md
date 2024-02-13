# Интенсив по Java Y_lab

# Веб-сервис для подачи показаний счетчиков отопления, горячей и холодной воды

## Запуск приложения

1. Клонируйте репозиторий: `git clone https://github.com/E6L1PS/monitoring-api.git`
2. Перейдите в директорию проекта: `cd monitoring-api`
3. Чтобы запустить тесты, выполните команду: `./gradlew test`
4. Если требуется, измените файлы конфигурации `.env`, `application.properties`, `liquibase.properties`.
5. Запустите контейнеры: `docker compose up --build -d`
6. Приложение будет доступно по адресу `http://localhost:8080/app`
7. Регистрация `http://localhost:8080/app/register`
8. Аутентификация `http://localhost:8080/app/login`
9. Эндпоинт показаний `http://localhost:8080/app/meter`
10. Эндпоинт типов показаний `http://localhost:8080/app/type`
11. Эндпоинт аудитов `http://localhost:8080/app/audit`

## Entity Relationship Diagram

![ERD](src/main/resources/MonitoringERD.png)

## Используемый стек:

- Docker
- ServletAPI
- AspectJ
- PostgreSQL JDBC Driver
- Liquibase
- MapStruct
- Lombok
- Slf4j
- Тестирование:
    - Testcontainers для PostgreSQL
    - AssertJ
    - Mockito
    - JUnit 5

## Tasks:

1. [Task-1](https://github.com/E6L1PS/monitoring-api/tree/task-1) (Вводная)
2. [Task-2](https://github.com/E6L1PS/monitoring-api/tree/task-2) (JDBC. Миграции БД)
3. [Task-3](https://github.com/E6L1PS/monitoring-api/pull/3) (Сервлеты. АОП)


