FROM docker.io/maven:3.9-eclipse-temurin-25-alpine AS builder

WORKDIR /builder

COPY pom.xml .

RUN mvn dependency:go-offline -B

COPY src ./src

RUN mvn package -DskipTests

FROM docker.io/eclipse-temurin:25-jre-alpine AS runner

RUN addgroup -S appgroup && adduser -S appuser -G appgroup

WORKDIR /app

COPY --from=builder /builder/target/todo-list-*.jar ./todo-list-api.jar

EXPOSE 8080

USER appuser

ENTRYPOINT [ "java", "-jar", "todo-list-api.jar" ]

LABEL org.opencontainers.image.title="To-Do List API" \
    org.opencontainers.image.version="0.0.1"