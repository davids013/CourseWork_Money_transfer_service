FROM openjdk:8-jdk-alpine

EXPOSE 5500

WORKDIR appDir

ADD target/Money_transfer_service-0.0.1-SNAPSHOT.jar app.jar

CMD ["java", "-jar", "app.jar"]