FROM openjdk:21-jdk-slim
LABEL version="1.0-test"
WORKDIR /app
COPY target/TaskManagerApp-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]
