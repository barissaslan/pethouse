FROM openjdk:11
MAINTAINER barisaslan.com
ARG JAR_FILE
COPY ${JAR_FILE} pethouse.jar
ENTRYPOINT ["java", "-jar", "pethouse.jar"]