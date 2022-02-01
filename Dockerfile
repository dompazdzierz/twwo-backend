
FROM maven:3.8.4-openjdk-17-slim AS build
COPY . /home/app
RUN mvn -f /home/app/pom.xml package -DskipTests

FROM openjdk:11-ea-17-jre-slim
COPY --from=build /home/app/target/demo-0.0.1-SNAPSHOT.jar /usr/local/lib/demo.jar
COPY --from=build /home/app/wait-for-it.sh /var/wait-for-it.sh
EXPOSE 8080
ENTRYPOINT ["java","-jar", "/usr/local/lib/demo.jar"]