FROM openjdk:18

VOLUME /tmp

COPY ./target/apigateway-0.0.1-SNAPSHOT.jar apigateway-0.0.1-SNAPSHOT.jar

EXPOSE 8079

ENTRYPOINT ["java", "-jar", "apigateway-0.0.1-SNAPSHOT.jar"]