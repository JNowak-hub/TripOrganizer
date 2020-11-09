FROM maven:3.6.3-jdk-11 AS build
ADD . /backend
WORKDIR /backend
RUN mvn install
RUN mv /backend/target/travelorganizer-*.jar /backend/backend.jar
#FROM gcr.io/distroless/java:11
FROM maven:3.6.3-jdk-11
COPY --from=build /backend/backend.jar /backend/backend.jar
WORKDIR /backend
CMD ["java", "-jar", "./backend.jar"]
