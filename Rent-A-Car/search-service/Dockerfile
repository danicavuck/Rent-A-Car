FROM openjdk:8-jdk-alpine
COPY entrypoint.sh /entrypoint.sh
COPY target/search-service-0.0.1-SNAPSHOT.jar search-service-0.0.1-SNAPSHOT.jar
EXPOSE 8088
RUN chmod +x /entrypoint.sh
CMD ["/entrypoint.sh"] 
