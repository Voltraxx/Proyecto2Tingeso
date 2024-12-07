FROM openjdk:17
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} user.jar
ENTRYPOINT ["java","-jar","/user.jar"]