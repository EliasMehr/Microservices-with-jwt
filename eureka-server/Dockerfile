FROM maven:3.6.3-openjdk-15-slim as jreBuilder

RUN mkdir /usr/src/project      
COPY . /usr/src/project
WORKDIR /usr/src/project
RUN mvn package -DskipTests

FROM adoptopenjdk/openjdk15:x86_64-alpine-jre-15.0.2_7
RUN mkdir /project
COPY --from=jreBuilder /usr/src/project/target/eureka-server-0.0.1-SNAPSHOT.jar /project/
WORKDIR /project
CMD java -jar eureka-server-0.0.1-SNAPSHOT.jar
