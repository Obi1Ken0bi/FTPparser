FROM openjdk:latest
VOLUME /tmp
COPY build/libs/*.jar FTPparser-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/FTPparser-0.0.1-SNAPSHOT.jar"]