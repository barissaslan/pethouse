FROM openjdk:11
MAINTAINER barisaslan.com
COPY build/libs/pethouse-1.0.0.jar pethouse.jar
ENTRYPOINT ["java", "-jar", "pethouse.jar"]