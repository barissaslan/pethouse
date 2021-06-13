FROM openjdk:11
MAINTAINER barisaslan.com
COPY target/pethouse-1.0.0.jar pethouse.jar
ENTRYPOINT ["java", "-jar", "/pethouse.jar"]