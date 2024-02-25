FROM gradle:7.5.1-jdk17-alpine AS build

WORKDIR /app

COPY build.gradle .
COPY settings.gradle .
COPY src ./src

RUN gradle build --refresh-dependencies -x test

FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]