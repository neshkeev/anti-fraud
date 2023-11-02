# Anti-fraud research project

The anti-fraud research project aims to explore the tech that is suitable for malicious activities detection

## Quickstart

1. Download [Amazon Corretto JDK 21](https://docs.aws.amazon.com/corretto/latest/corretto-21-ug/downloads-list.html):
```bash
curl -k -LO https://corretto.aws/downloads/latest/amazon-corretto-21-x64-linux-jdk.tar.gz
```
2. Extract the archive:
```bash
tar -xf amazon-corretto-21-*.tar.gz
``` 
3. Export `JAVA_HOME`:
```bash
export JAVA_HOME=./amazon-corretto-*/
```
4. Clone the project:
```bash
git clone https://github.com/neshkeev/anti-fraud.git
```
5. Enter the directory:
```bash
cd antifraud
```
5. Run the project:
```bash
./mvnw compile -pl anti-fraud-drools exec:java -Dexec.mainClass=com.githib.neshkeev.antifraud.Main
```

## Benchmarks

In order to run benchmarks execute:

```bash
./mvnw clean package -T 2C -PBenchmark &&
    java -XX:+HeapDumpOnOutOfMemoryError \
      -XX:HeapDumpPath=/tmp/DroolsRuleBenchmark-heapdump \
      -Xms2g \
      -Xmx2g \
      -jar benchmark/target/benchmarks.jar
```

## Temporal Workflows

There is a hello world temporal workflow. In order to start it, please execute the following commands:

1. Start a Temporal Server as a Docker service:

```bash
docker compose up -d
```

2. Build the project:

```bash
./mvnw clean package -T 2C
```

3. Start a worker:
```bash
./mvnw compile -pl anti-fraud-temporal-workflow exec:java -Dexec.mainClass=com.githib.neshkeev.antifraud.workflow.HelloWorldWorker
```

4. Run a couple workflows
```bash
./mvnw compile -pl anti-fraud-temporal-workflow exec:java -Dexec.mainClass=com.githib.neshkeev.antifraud.workflow.WorkflowInitiator
```

## Web Workflows

Workflows can be triggered with a REST API request. In order to start a web server, please execute the following commands:

1. Start a Temporal Server as a Docker service:

```bash
docker compose up -d
```

2. Build the project:

```bash
./mvnw clean package -T 2C
```

3. Start a worker:
```bash
java -jar anti-fraud-worker/target/anti-fraud-worker-0.0.1-SNAPSHOT.jar
```

4. Run the web server:
```bash
java -jar anti-fraud-web/target/anti-fraud-web-0.0.1-SNAPSHOT.jar
```

5. Trigger a workflow:
```bash
curl -v http://localhost:18080/check -H 'Content-Type: application/json' -d '{}'
```

6. Check the console with the worker

## Performance Testing

### Stop everything

```bash
docker compose down
docker compose --profile=base --profile=grpc --profile=rest --profile=perf --profile=worker -f ./docker-compose-perf.yml down
```

### Start the temporal server and postgres

```bash
docker compose --profile=base -f ./docker-compose-perf.yml up --build -d
```

### Start REST Workflow

```bash
TEMPORAL_ADDRESS=<TEMPORAL ADDRESS> docker compose --profile=rest -f ./docker-compose-perf.yml up --build -d
```

### Start GRPC Workflow

```bash
TEMPORAL_ADDRESS=<TEMPORAL ADDRESS> docker compose --profile=gprc -f ./docker-compose-perf.yml up --build -d
```

### Start Workers

```bash
TEMPORAL_ADDRESS=<TEMPORAL ADDRESS> POSTGRES_HOST=<POSTGRES HOST> docker compose --profile=worker -f ./docker-compose-perf.yml up --build -d
```

### Start REST Performance Testing

```bash
docker compose --profile=perf -f ./docker-compose-perf.yml down
TEMPORAL_WORKFLOW_WEB_ADDRESS=<REST WORKFLOW HOST:PORT> TEMPORAL_WORKFLOW_GRPC_ADDRESS=<GRPC WORKFLOW HOST:PORT> docker compose --profile=perf -f ./docker-compose-perf.yml up --build -d
TEMPORAL_WORKFLOW_WEB_ADDRESS=workflow:18080 TEMPORAL_WORKFLOW_GRPC_HOST=workflow-grpc:7777 docker compose --profile=perf -f ./docker-compose-perf.yml up --build -d
MSYS_NO_PATHCONV=1 docker compose -f ./docker-compose-perf.yml exec -it k6 k6 run --vus 100 --duration 30s /index.js
```

### Start GRPC Performance Testing

```bash
docker compose --profile=perf down
TEMPORAL_WORKFLOW_WEB_ADDRESS=<REST WORKFLOW HOST:PORT> TEMPORAL_WORKFLOW_GRPC_ADDRESS=<GRPC WORKFLOW HOST:PORT> docker compose --profile=perf -f ./docker-compose-perf.yml up --build -d
MSYS_NO_PATHCONV=1 docker compose -f ./docker-compose-perf.yml exec -it k6 k6 run --vus 100 --duration 30s /grpc.js
```

### Stop Everything
```
docker compose --profile=base --profile=grpc --profile=rest --profile=perf --profile=worker -f ./docker-compose-perf.yml down
docker compose down
```
