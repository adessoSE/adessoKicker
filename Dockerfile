FROM openjdk:11
FROM maven

WORKDIR /kicker

COPY . /kicker

RUN mvn clean:clean

RUN mvn package

EXPOSE 80

ENTRYPOINT ["java", "-jar", "target/kicker.jar"]
