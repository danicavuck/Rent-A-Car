FROM openjdk:8-jdk-alpine
COPY entrypoint.sh /entrypoint.sh
COPY target/messaging-service-0.0.1-SNAPSHOT.jar messaging-service-0.0.1-SNAPSHOT.jar
EXPOSE 8083
RUN chmod +x /entrypoint.sh
CMD ["/entrypoint.sh"] 
