FROM maven:3.6.3-openjdk-14-slim AS build-env

COPY ./pom.xml ./pom.xml
RUN mvn dependency:go-offline -B

COPY ./src ./src
RUN mvn package

FROM openjdk:14-alpine
WORKDIR /app

COPY --from=build-env ./target/app.jar app.jar
EXPOSE 8010

VOLUME data
ENV url=jdbc:sqlite:/data/contacts.db

CMD ["java","-jar","app.jar"]


