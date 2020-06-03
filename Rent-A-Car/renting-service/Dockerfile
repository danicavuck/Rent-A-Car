FROM openjdk:8-jdk-alpine
COPY entrypoint.sh /entrypoint.sh
COPY target/renting-service-0.0.1-SNAPSHOT.jar renting-service-0.0.1-SNAPSHOT.jar
EXPOSE 8086
RUN chmod +x /entrypoint.sh
CMD ["/entrypoint.sh"] 
