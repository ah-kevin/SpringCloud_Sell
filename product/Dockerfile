# Start with a base image containing Java runtime
FROM 192.168.76.61:8080/library/openjdk:8-jdk-alpine

# Add Maintainer Info
LABEL maintainer="bjke@iflytek.com"

# Add a volume pointing to /tmp
VOLUME /tmp

# Make port 8080 available to the world outside this container
EXPOSE 8080

# The application's jar file
ARG JAR_FILE=server/target/product-server-0.0.1-SNAPSHOT.jar

# Add the application's jar to the container
ADD ${JAR_FILE} product-server.jar

# Run the jar file
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/product-server.jar"]
