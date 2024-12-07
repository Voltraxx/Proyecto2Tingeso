FROM openjdk:17
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} evaluation.jar
ENTRYPOINT ["java","-jar","/evaluation.jar"]