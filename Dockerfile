FROM maven:3.6.3-openjdk-14-slim

COPY ./pom.xml ./pom.xml
RUN mvn dependency:go-offline -B

COPY ./src ./src
RUN mvn package

FROM openjdk:14-slim
COPY --from=0 ./target/app.jar app.jar
EXPOSE 8010
CMD ["java","-jar","app.jar"]


