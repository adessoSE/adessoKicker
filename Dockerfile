FROM openjdk:11-slim as build
WORKDIR /kicker

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN ./mvnw install -Dmaven.test.skip
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*jar)

FROM openjdk:11-slim

VOLUME /tmp
VOLUME /logs
VOLUME /db

ARG DEPENDENCY=/kicker/target/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /kicker/lib
COPY --from=build ${DEPENDENCY}/META-INF /kicker/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /kicker

EXPOSE 80

ENTRYPOINT ["java","-cp","kicker:kicker/lib/*","-Dspring.profiles.active=prod","de.adesso.kicker.Application"]