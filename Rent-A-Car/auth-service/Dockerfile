FROM openjdk:8-jdk-alpine
COPY entrypoint.sh /entrypoint.sh
COPY target/auth-service-0.0.1-SNAPSHOT.jar auth-service-0.0.1-SNAPSHOT.jar
EXPOSE 8081
RUN chmod +x /entrypoint.sh
CMD ["/entrypoint.sh"] 
