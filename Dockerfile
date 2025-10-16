# 1️⃣ Build stage - use full JDK for compiling
FROM eclipse-temurin:22-jdk AS build
WORKDIR /app

# 2️⃣ Copy Gradle wrapper and grant permission first (for caching + fix)
COPY gradlew .
COPY gradle gradle
RUN chmod +x gradlew

# 3️⃣ Copy the rest of the project and build
COPY . .
RUN ./gradlew bootJar --no-daemon

# 4️⃣ Run stage - lightweight JRE only
FROM eclipse-temurin:22-jre
WORKDIR /app

# Copy only the built JAR
COPY --from=build /app/build/libs/*.jar app.jar

# 5️⃣ Expose Spring Boot default port
EXPOSE 8080

# 6️⃣ Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
