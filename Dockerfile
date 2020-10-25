FROM gradle:6.7.0-jdk8  as gradle-builder
USER root
RUN mkdir ./randomizer
COPY build.gradle ./randomizer
COPY ./src ./randomizer/src
WORKDIR ./randomizer
RUN gradle build -x test

FROM java:8-jdk-alpine

COPY --from=gradle-builder /home/gradle/randomizer/build/libs/randomizer-0.0.1-SNAPSHOT.jar /opt/randomizer/
#COPY ./build/libs/randomizer-0.0.1-SNAPSHOT.jar /opt/randomizer/

CMD ["sh","-c","java -Xms512m -Xmx512m -jar /opt/randomizer/randomizer-0.0.1-SNAPSHOT.jar"]

EXPOSE 5000


