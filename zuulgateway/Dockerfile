#FROM maven:3.6.3-openjdk-15-slim AS build
##VOLUME /java-code
#RUN mkdir -p /workspace
#WORKDIR /workspace
#COPY pom.xml /workspace
#COPY src /workspace/src
#RUN mvn -f pom.xml clean package
#
#FROM openjdk:15-alpine
#COPY --from=build /workspace/target/*.jar app.jar
#EXPOSE 8080
#ENTRYPOINT ["java","-jar","app.jar"]

FROM maven:3.6.3-openjdk-15-slim
COPY . /usr/src/zuul-gateway
WORKDIR /usr/src/zuul-gateway
CMD ["mvn","spring-boot:run"]
