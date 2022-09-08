FROM gradle:7.5.0-jdk18 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle wrapper

FROM openjdk:18

EXPOSE 8081

RUN mkdir /app

COPY --from=build /home/gradle/src/build/libs/MC2-0.0.1-SNAPSHOT.jar /app/mc2.jar

ENTRYPOINT ["java", "-XX:+UnlockExperimentalVMOptions", "-Djava.security.egd=file:/dev/./urandom","-jar","/app/mc2.jar"]