FROM openjdk:8-jdk-alpine
COPY entrypoint.sh /entrypoint.sh
COPY target/image-service-0.0.1-SNAPSHOT.jar image-service-0.0.1-SNAPSHOT.jar
EXPOSE 8089
RUN chmod +x /entrypoint.sh
CMD ["/entrypoint.sh"] 
