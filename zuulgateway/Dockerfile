FROM maven:3.6.3-openjdk-15-slim as jreBuilder

RUN mkdir /usr/src/project      
COPY . /usr/src/project
WORKDIR /usr/src/project
RUN mvn clean package -DskipTests

FROM adoptopenjdk/openjdk15:x86_64-alpine-jre-15.0.2_7
RUN mkdir /project
COPY --from=jreBuilder /usr/src/project/target/zuulgateway-0.0.1-SNAPSHOT.jar /project/
WORKDIR /project
CMD java -jar zuulgateway-0.0.1-SNAPSHOT.jar

# FROM maven:3.6.3-openjdk-15-slim as jreBuilder

# RUN jlink \
#         --add-modules jdk.unsupported,java.sql,java.desktop,java.naming,java.management,java.instrument,java.security.jgss,java.rmi \
#         --verbose \
#         --strip-java-debug-attributes \
#         --compress 2 \
#         --no-header-files \
#         --no-man-pages \
#         --output /jre

# FROM openjdk:15.0-slim
# ARG JAR_FILE

# COPY --from=jreBuilder /jre /usr/lib/jre
# ENTRYPOINT ["/usr/lib/jre/bin/java", "-jar", "./app.jar"]
# COPY ./target/${JAR_FILE} ./app.jar




#FROM openjdk:15-jdk-slim as bulid
#
#WORKDIR application
#
#COPY mvnw .
#COPY .mvn .mvn
#COPY pom.xml .
#COPY src src
#
#RUN --mount=type=cache,target=/root/.m2 ./mvnw  install -DskipTests
#
#RUN cp /application/target/*.jar app.jar
#RUN java -Djarmode=layertools -jar app.jar extract
#
#FROM openjdk:15-jdk-slim
#WORKDIR application
#COPY --from=bulid application/dependencies/ ./
#COPY --from=bulid application/spring-boot-loader/ ./
#COPY --from=bulid application/snapshot-dependencies/ ./
#COPY --from=bulid application/application/ ./
#ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]