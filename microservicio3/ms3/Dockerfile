FROM openjdk:17
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} creditRequest.jar
ENTRYPOINT ["java","-jar","/creditRequest.jar"]