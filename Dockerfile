FROM maven:3.6.3-openjdk-11-slim AS build

WORKDIR /workspace

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src /workspace/src
COPY entrypoint.sh /workspace
RUN mvn -B package --file pom.xml -DskipTests

FROM openjdk:11-slim
COPY --from=build /workspace/target/cliente-api-*.jar app.jar
ADD entrypoint.sh entrypoint.sh
ENTRYPOINT ["/entrypoint.sh"]
RUN ["chmod", "+x", "/entrypoint.sh"]