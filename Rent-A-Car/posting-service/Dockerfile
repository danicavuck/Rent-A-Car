FROM openjdk:8-jdk-alpine
COPY entrypoint.sh /entrypoint.sh
COPY target/posting-service-0.0.1-SNAPSHOT.jar posting-service-0.0.1-SNAPSHOT.jar
EXPOSE 8085
RUN chmod +x /entrypoint.sh
CMD ["/entrypoint.sh"] 
