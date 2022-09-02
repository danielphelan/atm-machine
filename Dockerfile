FROM openjdk:8-jdk-alpine
ARG WAR_FILE=target/*.war
COPY ${WAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]