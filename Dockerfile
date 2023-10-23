FROM openjdk:19

ENV IMG_PATH=/img

EXPOSE 8080

RUN mkdir -p /img

ADD build/libs/recursos_humanos-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]