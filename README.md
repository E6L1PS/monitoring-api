# Интенсив по Java Y_lab

# Веб-сервис для подачи показаний счетчиков отопления, горячей и холодной воды

## Запуск приложения

1. Клонируйте репозиторий: `git clone https://github.com/E6L1PS/monitoring-api.git`
2. Перейдите в директорию проекта: `cd monitoring-api`
3. Чтобы запустить тесты, выполните команду: `./gradlew test`
4. Если требуется, измените файлы конфигурации `.env`, `application.yml`, `liquibase.properties`.
5. Запустите контейнеры: `docker compose up --build -d`
6. Приложение будет доступно по адресу `http://localhost:8080/app`
7. Регистрация `http://localhost:8080/app/auth/reg`
8. Аутентификация `http://localhost:8080/app/auth/login`
9. Эндпоинт показаний `http://localhost:8080/app/meter`
10. Эндпоинт типов показаний `http://localhost:8080/app/type`
11. Эндпоинт аудитов `http://localhost:8080/app/audit`
12. OpenApi Specification `http://localhost:8080/app/swagger-ui/index.html`

## Entity Relationship Diagram

![ERD](src/main/resources/MonitoringERD.png)

## Используемый стек:

- SpringContext
- SpringWeb
- SpringWebmvc
- SpringJdbc
- SpringAspects
- SpringSecurity
- JsonWebToken
- Docker
- PostgreSQL JDBC Driver
- HikariCP
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
3. [Task-3](https://github.com/E6L1PS/monitoring-api/tree/task-3) (Сервлеты. АОП)
4. [Task-3](https://github.com/E6L1PS/monitoring-api/pull/4) (Знакомство с Spring Framework)


