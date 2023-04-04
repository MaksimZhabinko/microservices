FROM openjdk:18

VOLUME /tmp

COPY ./target/one-0.0.1-SNAPSHOT.jar one-0.0.1-SNAPSHOT.jar

EXPOSE 8086

ENTRYPOINT ["java", "-jar", "one-0.0.1-SNAPSHOT.jar"]