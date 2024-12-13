

FROM maven:3.8-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY . .
RUN mvn clean package

FROM tomcat:latest

COPY target/TopicHub-backend-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/
EXPOSE 8080
