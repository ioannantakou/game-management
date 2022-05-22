FROM openjdk:17-oracle
MAINTAINER ioanna
COPY target/game-management-1.0.0.jar game-management-1.0.0.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/game-management-1.0.0.jar"]