FROM eclipse-temurin:17-jdk-jammy as builder
WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline

COPY src ./src
RUN ./mvnw clean install -Dmaven.test.skip=true

#CMD ["./mvnw", "spring-boot:run"]
CMD ["java", "-jar", "/app/target/modules-recommender-0.0.1.jar" ]

#WORKDIR /opt/app
#COPY .mvn/ .mvn
#COPY mvnw pom.xml ./
#RUN ./mvnw dependency:go-offline
#COPY ./src ./src
#RUN ./mvnw clean install -Dmaven.test.skip=true
#
#FROM eclipse-temurin:17-jre-jammy
#WORKDIR /opt/app
#EXPOSE 8081
#COPY --from=builder /opt/app/target/*.jar /opt/app/*.jar
#ENTRYPOINT ["java", "-jar", "/opt/app/*.jar" ]