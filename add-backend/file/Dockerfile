# FROM openjdk:19

FROM adoptopenjdk/openjdk11:alpine as build

WORKDIR /app

RUN apk update

# Install tesseract library
RUN apk add --no-cache tesseract-ocr

# Download last language package


# Check the installation status
RUN tesseract --list-langs
RUN tesseract -v


RUN  mkdir ./tesseract
COPY add-backend/file/tesseract ./tesseract
RUN  mkdir ./logs
RUN chmod -R 777 /app/logs
COPY add-backend/file/target/file.jar /app/app.jar
# COPY ./target/file.jar /app/app.jar
RUN chmod -R 777 /app/app.jar

# ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod","app.jar"]
ENTRYPOINT ["java","-jar","app.jar"]
