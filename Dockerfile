FROM openjdk:18-jdk-alpine
LABEL maintainer="he305@mail.ru"

COPY target/*.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]