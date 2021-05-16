FROM openjdk:11.0.6-jdk
COPY build/libs/DroneService-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "-Djdk.tls.client.protocols=TLSv1.2", "/app.jar"]