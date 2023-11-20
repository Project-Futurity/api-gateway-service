FROM openjdk:17-alpine

COPY target/api-gateway-1.0.jar application.jar

CMD java -jar /application.jar