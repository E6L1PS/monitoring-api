FROM gradle:7.5.1-jdk17-alpine AS build

WORKDIR /app

COPY di-container/build.gradle ./di-container/
COPY di-container/settings.gradle ./di-container/
COPY di-container/src ./di-container/src/
COPY build.gradle .
COPY settings.gradle .
COPY src ./src

RUN gradle build --refresh-dependencies -x test

FROM tomcat:jre17

WORKDIR /app

#COPY C:/Users/danil/.gradle/caches/modules-2/files-2.1/org.postgresql/postgresql/42.7.1/66098cc4f7dca6f7f5b4b847c5cc8699fc079e5b /usr/local/tomcat/lib/
COPY --from=build /app/build/libs/*war /usr/local/tomcat/webapps/app.war

EXPOSE 8080

CMD ["catalina.sh", "run"]


#FROM openjdk:17-jdk-slim
#
#WORKDIR /app
#
#COPY --from=build /app/build/libs/monitoring-api-1.0-SNAPSHOT.jar application.jar
#
#ENTRYPOINT ["java", "-jar", "application.jar"]