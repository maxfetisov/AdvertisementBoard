FROM maven:latest as build
WORKDIR .
COPY src /app/src
COPY pom.xml /app/pom.xml
RUN mvn -f /app/pom.xml clean package

FROM openjdk:19
COPY --from=build ./target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app/app.jar"]