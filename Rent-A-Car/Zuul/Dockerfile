FROM openjdk:8-jdk-alpine
COPY entrypoint.sh /entrypoint.sh
COPY target/Zuul-0.0.1-SNAPSHOT.jar Zuul-0.0.1-SNAPSHOT.jar
EXPOSE 8080
RUN chmod +x /entrypoint.sh
CMD ["/entrypoint.sh"] 
