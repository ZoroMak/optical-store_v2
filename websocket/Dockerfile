FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM amazoncorretto:17 AS runtime
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8088

ENV JAVA_TOOL_OPTIONS -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005

CMD ["java", "-jar", "app.jar"]
