# 1️⃣ Use official OpenJDK image
FROM eclipse-temurin:22-jdk AS build

# 2️⃣ Set working directory
WORKDIR /app

# 3️⃣ Copy Gradle wrapper and set execute permission
COPY gradlew .
COPY gradle gradle
RUN chmod +x gradlew

# 4️⃣ Copy the rest of the project and build
COPY . .
RUN ./gradlew bootJar --no-daemon

# 5️⃣ Run stage - lightweight JRE only
FROM eclipse-temurin:22-jre
WORKDIR /app

# Copy JAR from build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Expose port (match your Spring Boot server.port)
EXPOSE 8080

# Start the app
ENTRYPOINT ["java", "-jar", "app.jar"]
