FROM openjdk:17
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} ms-cars.jar
ENTRYPOINT ["java","-jar","/ms-cars.jar"]