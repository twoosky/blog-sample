FROM openjdk:17-alpine

WORKDIR /build

COPY ./build/libs/docker-nginx-0.0.1-SNAPSHOT.jar docker-nginx-0.0.1-SNAPSHOT.jar

COPY ./src/main/resources ./server/src/main/resources

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "docker-nginx-0.0.1-SNAPSHOT.jar"]