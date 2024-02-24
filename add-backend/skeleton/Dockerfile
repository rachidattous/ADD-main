# Production Dockerfile
FROM maven:3.8.1-jdk-11 AS builder

WORKDIR /app
COPY ./ /app
RUN mvn clean compile package -Dmaven.test.skip=true
RUN ls /app/target/notes-0.0.1-SNAPSHOT.jar


FROM adoptopenjdk/openjdk11:alpine-jre
COPY --from=builder /app/target/notes-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod","app.jar"]
