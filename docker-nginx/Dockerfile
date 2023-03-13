FROM openjdk:17-alpine

WORKDIR /build

COPY ./build/libs/docker-nginx-0.0.1-SNAPSHOT.jar docker-nginx-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "docker-nginx-0.0.1-SNAPSHOT.jar"]