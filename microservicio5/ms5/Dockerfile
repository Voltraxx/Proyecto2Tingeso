FROM openjdk:17
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} followUp.jar
ENTRYPOINT ["java","-jar","/followUp.jar"]