# 1️⃣ Use Gradle official image for build stage
FROM gradle:8.9-jdk22 AS build
WORKDIR /app

# Copy only what’s needed first
COPY build.gradle settings.gradle ./
COPY src ./src

# Build using Gradle (no wrapper)
RUN gradle bootJar --no-daemon

# 2️⃣ Run stage - lightweight JRE only
FROM eclipse-temurin:22-jre
WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
