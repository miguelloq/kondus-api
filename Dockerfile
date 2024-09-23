FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY . .

RUN ./gradlew build

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "./build/libs/Kondus-api-0.0.1-SNAPSHOT.jar"]
