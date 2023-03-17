FROM openjdk:17-alpine

WORKDIR /app
#
#COPY ./build/libs/docker-nginx-0.0.1-SNAPSHOT.jar docker-nginx-0.0.1-SNAPSHOT.jar
#
#COPY . .

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "/build/libs/docker-nginx-0.0.1-SNAPSHOT.jar"]