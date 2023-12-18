FROM ubuntu:latest AS build

RUN apt-get update
RUN apt-get install openjdk-17-jdk -y
COPY . .

RUN apt-get install maven -y
RUN mvn clean install -DskipTests

FROM openjdk:17-jdk-slim

EXPOSE 3000

COPY --from=build /target/docker-spring-boot.jar app.jar
ENTRYPOINT ["java", "-jar", "docker-spring-boot.jar"]