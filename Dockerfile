# --- Builder Stage ---
FROM eclipse-temurin:21-jdk-alpine AS builder

WORKDIR /app

COPY gradlew .
COPY gradle gradle
COPY app/build.gradle .
COPY settings.gradle .
COPY gradle.properties .


COPY app app

RUN chmod +x ./gradlew

RUN ./gradlew clean bootJar --no-daemon -x test

# --- Runtime Stage ---

FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=builder /app/app/build/libs/*.jar app.jar


EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]

