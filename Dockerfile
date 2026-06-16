# Use the official Eclipse Temurin (OpenJDK) 21 image
FROM eclipse-temurin:21-jdk-jammy

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven wrapper and project configuration files first
# This leverages Docker layer caching to speed up future builds
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./

# Give execute permission to the Maven wrapper
RUN chmod +x mvnw

# Download dependencies (this step is cached unless pom.xml changes)
RUN ./mvnw dependency:go-offline

# Copy the source code
COPY src ./src

# Build the application, skipping tests to speed up the deploy process
# If you prefer to run tests during the build, remove -DskipTests
RUN ./mvnw clean package -DskipTests

# Move the built JAR to a known location
RUN mv target/*.jar app.jar

# Expose the port your Spring Boot app runs on (default is 8080)
EXPOSE 8080

# The command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]