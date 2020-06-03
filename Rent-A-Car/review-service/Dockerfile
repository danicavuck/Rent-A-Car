FROM openjdk:8-jdk-alpine
COPY entrypoint.sh /entrypoint.sh
COPY target/review-service-0.0.1-SNAPSHOT.jar review-service-0.0.1-SNAPSHOT.jar
EXPOSE 8087
RUN chmod +x /entrypoint.sh
CMD ["/entrypoint.sh"] 
