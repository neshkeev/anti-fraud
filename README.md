# Antifraud research project

The antifraud research project amis to explore the tech that is suitable for malicious activities detection

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
git clone https://github.com/neshkeev/antifraud.git
```
5. Enter the directory:
```bash
cd antifraud
```
5. Run the project:
```bash
./mvnw compile -pl antifraud-drools exec:java -Dexec.mainClass=com.githib.neshkeev.antifraud.Main
```

## Benchmarks

In order to run benchmarks execute:

```bash
./mvnw clean package -T 2C &&
    java -XX:+HeapDumpOnOutOfMemoryError \
      -XX:HeapDumpPath=/tmp/DroolsRuleBenchmark-heapdump \
      -Xms2g \
      -Xmx2g \
      -jar benchmark/target/benchmarks.jar
```
