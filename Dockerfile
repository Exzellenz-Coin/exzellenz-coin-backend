FROM maven:3.6.3-openjdk-16 AS MAVEN_TOOL_CHAIN
COPY pom.xml /tmp/
COPY src /tmp/src/
WORKDIR /tmp/
RUN mvn -T 4C install

FROM openjdk:16-alpine
WORKDIR /app
COPY --from=MAVEN_TOOL_CHAIN /tmp/target/ExcellenceCoinBackend-*.jar app.jar
RUN mkdir /data
EXPOSE 8080
EXPOSE 8081
CMD java -jar /app/app.jar db migrate /data/config.yml && java -jar /app/app.jar /data/config.yml
