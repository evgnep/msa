FROM bellsoft/liberica-openjdk-alpine:17
COPY target/demo-0.0.1-SNAPSHOT.jar /app/

ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-jar", "/app/demo-0.0.1-SNAPSHOT.jar"]
