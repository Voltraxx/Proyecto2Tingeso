FROM openjdk:17
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} totalCost.jar
ENTRYPOINT ["java","-jar","/totalCost.jar"]