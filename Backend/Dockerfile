FROM openjdk:17-alpine
WORKDIR /app
COPY Backend/target/lightfeather-0.0.1-SNAPSHOT.jar /app/lightfeather-0.0.1-SNAPSHOT.jar
EXPOSE 8080
CMD ["java", "-jar", "lightfeather-0.0.1-SNAPSHOT.jar"]