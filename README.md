# Интенсив по Java Y_lab
# Веб-сервис для подачи показаний счетчиков отопления, горячей и холодной воды

## Запуск приложения

1. Клонируйте репозиторий: `git clone https://github.com/E6L1PS/monitoring-api.git`
2. Перейдите в директорию проекта: `cd monitoring-api`
3. Запустите приложение: `./gradlew run`
4. Чтобы запустить тесты, выполните команду: `./gradlew test`
5. `docker compose up --build -d`
6. `docker-compose exec -i api-node-1 java -jar application.jar`

![ERD](src/main/resources/MonitoringERD.svg)

