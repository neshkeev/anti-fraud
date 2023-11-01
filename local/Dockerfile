# syntax=docker/dockerfile:1
ARG APP_PATH=/java/project

FROM amazoncorretto:21.0.1-al2023 as builder

ARG APP_PATH
WORKDIR ${APP_PATH}

COPY mvnw mvnw
COPY .mvn .mvn

COPY pom.xml pom.xml
COPY anti-fraud-drools/pom.xml anti-fraud-drools/pom.xml 
COPY anti-fraud-temporal-contract/pom.xml anti-fraud-temporal-contract/pom.xml
COPY anti-fraud-web/pom.xml anti-fraud-web/pom.xml
COPY anti-fraud-worker/pom.xml anti-fraud-worker/pom.xml
COPY benchmark/pom.xml benchmark/pom.xml

RUN --mount=type=cache,target=/root/.m2 \
     ./mvnw -B dependency:go-offline -T 4C

COPY anti-fraud-drools/src anti-fraud-drools/src 
COPY anti-fraud-temporal-contract/src anti-fraud-temporal-contract/src
COPY anti-fraud-web/src anti-fraud-web/src
COPY anti-fraud-worker/src anti-fraud-worker/src
COPY benchmark/src benchmark/src

RUN --mount=type=cache,target=/root/.m2 \
    ./mvnw -B package -DskipTests=true -T 2C && \
    true

FROM amazoncorretto:21.0.1-al2023 as runner

ARG APP_PATH
WORKDIR ${APP_PATH}

COPY --from=builder $APP_PATH/anti-fraud-web/target/anti-fraud-web*.jar anti-fraud-web.jar 
COPY --from=builder $APP_PATH/anti-fraud-worker/target/anti-fraud-worker*.jar anti-fraud-worker.jar

# Add Tini
ENV TINI_VERSION v0.19.0
ADD --chmod=777 https://github.com/krallin/tini/releases/download/${TINI_VERSION}/tini /tini

ENTRYPOINT ["/tini", "--", "java", "-jar"]
CMD ["anti-fraud-web.jar"]
