# Start with a base image containing Java runtime
FROM maven:3.8-openjdk-17 as builder
ENV SPRING_MYSQL_HOST=solosavings-db

# Set the working directory in the image to /app
WORKDIR /app

# Copy maven executable to the image
COPY mvnw .
COPY .mvn .mvn

# Copy the pom.xml file
COPY pom.xml .

# Build all dependencies for offline use
RUN mvn -B dependency:resolve dependency:resolve-plugins

# Copy your other files
COPY src src

# Build the project and generate the .jar file
RUN mvn package -DskipTests

# Start with a base image containing Java runtime
FROM openjdk:17-jdk
ENV SPRING_MYSQL_HOST=solosavings-db
# Copy the jar from builder to the new image
COPY --from=builder /app/target/*.jar app.jar

# Run the jar file 
ENTRYPOINT ["java","-jar","/app.jar"]