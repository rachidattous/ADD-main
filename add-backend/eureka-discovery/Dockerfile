FROM openjdk:19

WORKDIR /app
RUN  mkdir ./logs
RUN chmod -R 777 /app/logs
COPY add-backend/eureka-discovery/target/eureka.jar /app/app.jar
RUN chmod -R 777 /app/app.jar
# ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod","app.jar"]
ENTRYPOINT ["java","-jar","app.jar"]