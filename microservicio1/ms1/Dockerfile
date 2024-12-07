FROM openjdk:17
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} simulation.jar
ENTRYPOINT ["java","-jar","/simulation.jar"]