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

COPY --from=build /app/build/libs/*war /usr/local/tomcat/webapps/app.war

EXPOSE 8080

CMD ["catalina.sh", "run"]