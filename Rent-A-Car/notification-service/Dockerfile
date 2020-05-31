FROM openjdk:8-jdk-alpine
COPY entrypoint.sh /entrypoint.sh
COPY target/notification-service-0.0.1-SNAPSHOT.jar notification-service-0.0.1-SNAPSHOT.jar
EXPOSE 8084
RUN chmod +x /entrypoint.sh
CMD ["/entrypoint.sh"] 
