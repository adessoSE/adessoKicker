# syntax=docker/dockerfile:experimental
FROM openjdk:11-slim as build
WORKDIR /kicker

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN --mount=type=cache,target=/root/.m2 ./mvnw install -Dmaven.test.skip
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*jar)

FROM openjdk:11-jre-slim
WORKDIR /kicker

VOLUME /tmp
VOLUME ./logs
VOLUME ./db

ARG DEPENDENCY=./kicker/target/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib ./lib
COPY --from=build ${DEPENDENCY}/META-INF ./META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes ./

EXPOSE 80

ENTRYPOINT ["java","-cp","./:./lib/*","-Dspring.profiles.active=prod","de.adesso.kicker.Application","--spring.config.location=classpath:/"]