FROM openjdk:8-jdk-alpine
COPY entrypoint.sh /entrypoint.sh
COPY target/admin-service-0.0.1-SNAPSHOT.jar admin-service-0.0.1-SNAPSHOT.jar
EXPOSE 8082
RUN chmod +x /entrypoint.sh
CMD ["/entrypoint.sh"]
