FROM maven:3.9.5-eclipse-temurin-21 AS builder

WORKDIR /app
COPY pom.xml ./
COPY src ./src

RUN mvn clean package -DskipTests


FROM openjdk:21-jdk-slim

WORKDIR /app
COPY --from=builder /app/target/TaskManagerApp-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8083
ENTRYPOINT ["java", "-jar", "app.jar"]
