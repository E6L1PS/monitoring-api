FROM gradle:7.5.1-jdk17-alpine AS build

WORKDIR /app

COPY auditing-spring-boot-starter/src ./auditing-spring-boot-starter/src/
COPY auditing-spring-boot-starter/build.gradle ./auditing-spring-boot-starter/

COPY logging-spring-boot-starter/src ./logging-spring-boot-starter/src/
COPY logging-spring-boot-starter/build.gradle ./logging-spring-boot-starter/

COPY src ./src
COPY build.gradle .

COPY settings.gradle .

RUN gradle build -x test

FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=build /app/build/libs/monitoring-api-1.0-SNAPSHOT.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]