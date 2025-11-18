    FROM openjdk:26-ea-21-jdk-slim

    VOLUME /tmp

    COPY target/meridian-1.0-SNAPSHOT.jar app.jar

    ENTRYPOINT ["java","-jar","/app.jar"]