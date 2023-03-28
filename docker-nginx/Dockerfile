FROM openjdk:17-alpine

WORKDIR /app

COPY ./build/libs/docker-nginx-0.0.1-SNAPSHOT.jar app.jar

#ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "app.jar"]

ENTRYPOINT ["java", "-jar", "app.jar"]