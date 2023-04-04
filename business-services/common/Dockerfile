FROM openjdk:18

VOLUME /tmp

COPY ./target/common-0.0.1-SNAPSHOT.jar common-0.0.1-SNAPSHOT.jar
COPY ./target/classes/internal.properties internal.properties

EXPOSE 8090

ENTRYPOINT ["java", "-jar", "common-0.0.1-SNAPSHOT.jar"]