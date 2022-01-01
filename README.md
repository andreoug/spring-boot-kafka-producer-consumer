# Spring Boot with Kafka for SMS Delivery

This is a pilot repo is utilizing Spring Boot 2.5.6 and Kafka broker for the use case of *SMS Delivery*. 

If you are an action first words later person or you prefer the Prof of Concept before the Analysis, your next stop 
is going to be section [Getting Started](#gs) in order to make this application run locally on your machine and then 
return to the documentation for more enlightening details.  

In the scopes of this pilot is to dive into the Kafka brokers messaging with Spring Boot and Java. Secondly,
to be used as a testbed for any forthcoming Spring Boot updates, and finally, to build a use case, which will 
be used as a working example app or a starting point for any further plannings and implementations. 

## Introduction

While explaining the following activity diagram, a *Sender* sends a request to a web API to post a message Body 
to a *Receiver*. The API call triggers the *Producer* to send an *Action* object containing the SMS to kafka 
brokers and the *Consumer* reduces from Kafka the action to delegate the SMS. The delegation, for the time being, 
is just a log prompt on the consumer. 

![Activity Diagram](http://www.plantuml.com/plantuml/proxy?src=https://raw.githubusercontent.com/andreoug/spring-boot-kafka-producer-consumer/main/docs/umls/sms-delivery-sequence-diagram.puml)

The *producer* and the *consumer* are two Spring profiles loaded on this application as we will 
explain later on the section about [Spring Boot Profiles](#sbp).


## <a name="gs"> 1. Getting Started</a>
The easiest way to pull, deploy and run this produce-consume kafka string message on your 
laptop or any other clean system is through Staging environment, where only docker 
installation is required. 

### 1.1. Working Environments
Currently, the working environments are the following.
- Staging Environment,
- Development Environment.

The key differences between the working environments (dev and staging) are the following:
- The containerization on staging of the app as you can figure out on [docker-compose.yml](docker-compose.yml) and 
[docker-compose-kafka-only.yml](docker-compose-kafka-only.yml)
- The seperated network that is *kafka-net* network defined in [docker-compose.yml](docker-compose.yml)

#### 1.1.1. Prerequisites
As you can figure out in the following table, the least prerequisites to give a try to this 
repo are with staging environment. But if you have already java and maven experience, 
development environment is similarly easy to build and experiment.

##### 1.1.1.1. Prerequisites Details
| Framework | Staging | Development |
| :-------------- | :----: | :-----: |
| docker         | &checkmark;  | &checkmark;|
| docker-compose | &checkmark;  | &checkmark; |
| maven          |              | &checkmark; |
| java           |              | &checkmark;|

#### 1.1.2. Staging Environment
Follow the steps on the section about [Working on the Staging Environment](#wotse).

##### 1.1.2.1. Versioning Details

| Program | Package | Version | Ruled by |
| :---- | :------- | -------: | --------: |
| docker | Docker CE | 20.10.8 | env. |
| docker-compose | Docker CE | 1.29.2 | env. |
| Kafka | Kafka Broker | - | [docker-compose.yml](docker-compose.yml) |
| mvn | Maven | - | [Dockerfile](Dockerfile) |
| java | Java | - | [Dockerfile](Dockerfile) |
| spring-boot | Spring-Boot-Starter-Parent | 2.5.6 | [pom.xml](pom.xml) |
| lib | Spring-Boot-Starter-Web | - | spring-boot |
| lib | Spring-Kafka | - | spring-boot |
| lib | Lombok | - | spring-boot |
| lib | openapi-ui | 1.5.2 | [pom.xml](pom.xml) |

#### 1.1.3. Development Environment
Follow the steps on section about [Working on the Development Environment](#wotde).

##### 1.1.3.1 Versioning Details

| Program | Package | Version | Ruled by |
| :---- | :------- | -------: | --------: |
| docker | Docker CE | 20.10.8 | env. |
| docker-compose | Docker CE | 1.29.2 | env. |
| Kafka | Kafka Broker | - | [docker-compose.yml](docker-compose.yml) |
| mvn | Maven | 3.8.1 | env. |
| java | Java | 1.8.0_292 | env. |
| spring-boot | Spring-Boot-Starter-Parent | 2.5.6 | [pom.xml](pom.xml) |
| lib | Spring-Boot-Starter-Web | - | spring-boot |
| lib | Spring-Kafka | - | spring-boot |
| lib | Lombok | - | spring-boot |
| lib | openapi-ui | 1.5.2 | [pom.xml](pom.xml) |


### <a name="wotse">1.2. Working on the Staging Environment</a>

1. Deploy the Kafka broker with Zookeeper and also producer and consumer of the app using docker-compose as follows

    ```bash
      docker-compose up -d
    ```

3. Send a sms to the app to produce and consume it as follows

    ```bash
      curl -X POST "http://localhost:9000/kafka/send-sms" -H  "accept: */*" \
      -H  "Content-Type: application/json" \
      -d "{\"body\":\"string\",\"sender\":\"0123456789\",\"receiver\":\"0123456789\"}"
    ```

4. Check the logs from producer through docker-compose logs

    ```bash
        $ docker-compose logs producer
        ...
        producer     | 2021-12-31 16:04:00.794  INFO 1 --- [nio-9000-exec-1] c.p.s.Producer                           : #~#: Producing action -> Action(id=7db7ea28, sms=Sms(body=string, sender=0123456789, receiver=0123456789, timestamp=2021-12-31T16:04:00.788), created=2021-12-31T16:04:00.793, updated=2021-12-31T16:04:00.793, status=CREATED)
        producer     | 2021-12-31 16:04:00.952  INFO 1 --- [nio-9000-exec-1] o.a.k.clients.producer.ProducerConfig    : ProducerConfig values:

        ...
        producer     | 2021-12-31 16:04:01.363  INFO 1 --- [nio-9000-exec-1] o.a.kafka.common.utils.AppInfoParser     : Kafka version: 2.7.1
        producer     | 2021-12-31 16:04:01.370  INFO 1 --- [nio-9000-exec-1] o.a.kafka.common.utils.AppInfoParser     : Kafka commitId: 61dbce85d0d41457
        producer     | 2021-12-31 16:04:01.371  INFO 1 --- [nio-9000-exec-1] o.a.kafka.common.utils.AppInfoParser     : Kafka startTimeMs: 1640966641319
        producer     | 2021-12-31 16:04:02.638  INFO 1 --- [ad | producer-1] org.apache.kafka.clients.Metadata        : [Producer clientId=producer-1] Cluster ID: 3Y_YUw5wQsauf5zR3_Njjw
    ```

5. Check the logs from consumer to see the consumed message through docker-compose logs

    ```bash
        $ docker-compose logs consumer
        ...
        consumer     | 2021-12-31 16:03:51.930  INFO 1 --- [ntainer#0-0-C-1] o.a.k.c.c.internals.ConsumerCoordinator  : [Consumer clientId=consumer-group_id-1, groupId=group_id] Adding newly assigned partitions: messages-0
        consumer     | 2021-12-31 16:03:51.986  INFO 1 --- [ntainer#0-0-C-1] o.a.k.c.c.internals.ConsumerCoordinator  : [Consumer clientId=consumer-group_id-1, groupId=group_id] Found no committed offset for partition messages-0
        consumer     | 2021-12-31 16:03:52.026  INFO 1 --- [ntainer#0-0-C-1] o.a.k.c.c.internals.SubscriptionState    : [Consumer clientId=consumer-group_id-1, groupId=group_id] Resetting offset for partition messages-0 to position FetchPosition{offset=0, offsetEpoch=Optional.empty, currentLeader=LeaderAndEpoch{leader=Optional[kafka:9092 (id: 1001 rack: null)], epoch=0}}.
        consumer     | 2021-12-31 16:03:52.028  INFO 1 --- [ntainer#0-0-C-1] o.s.k.l.KafkaMessageListenerContainer    : group_id: partitions assigned: [messages-0]
        consumer     | 2021-12-31 16:04:03.175  INFO 1 --- [ntainer#0-0-C-1] c.p.s.Consumer                           : #~#: Consumed action -> Action(id=7db7ea28, sms=Sms(body=string, sender=0123456789, receiver=0123456789, timestamp=2021-12-31T16:04), created=2021-12-31T16:04, updated=2021-12-31T16:04, status=CREATED)   
    ```

### <a name="wotde">1.3. Working on the Development Environment</a>

1. Deploy the Kafka broker with Zookeeper using docker-compose as follows

    ```bash
      docker-compose -f docker-compose-kafka-only.yml up -d
    ```
2. Use Maven to clean and package your app as follows 
    ```bash
      mvn clean package
    ```
3. Run your app with java as follows

    ```bash
      java -jar target/*.jar
    ```
4. Send a message to the app to produce and consume it as follows

```bash
  curl -X POST "http://localhost:9000/kafka/send-sms" -H  "accept: */*" \
  -H  "Content-Type: application/json" \
  -d "{\"body\":\"string\",\"sender\":\"0123456789\",\"receiver\":\"0123456789\"}"
```

5. Check you logs

    ```bash
        ...
        2021-12-31 18:11:21.357  INFO 73107 --- [nio-9000-exec-1] c.p.s.Producer                           : #~#: Producing action -> Action(id=acc55adf, sms=Sms(body=string, sender=0123456789, receiver=0123456789, timestamp=2021-12-31T18:11:21.355), created=2021-12-31T18:11:21.356, updated=2021-12-31T18:11:21.356, status=CREATED)
        2021-12-31 18:11:21.368  INFO 73107 --- [nio-9000-exec-1] o.a.k.clients.producer.ProducerConfig    : ProducerConfig values:
        ...
        2021-12-31 18:11:22.489  INFO 73107 --- [nio-9000-exec-1] o.a.kafka.common.utils.AppInfoParser     : Kafka version: 2.7.1
        2021-12-31 18:11:22.489  INFO 73107 --- [nio-9000-exec-1] o.a.kafka.common.utils.AppInfoParser     : Kafka commitId: 61dbce85d0d41457
        2021-12-31 18:11:22.489  INFO 73107 --- [nio-9000-exec-1] o.a.kafka.common.utils.AppInfoParser     : Kafka startTimeMs: 1640967082489
        2021-12-31 18:11:22.504  INFO 73107 --- [ad | producer-1] org.apache.kafka.clients.Metadata        : [Producer clientId=producer-1] Cluster ID: J4FEAYQsT7WvOE4mqNxvwg
        2021-12-31 18:11:22.710  INFO 73107 --- [ntainer#0-0-C-1] c.p.s.Consumer                           : #~#: Consumed action -> Action(id=acc55adf, sms=Sms(body=string, sender=0123456789, receiver=0123456789, timestamp=2021-12-31T18:11:21), created=2021-12-31T18:11:21, updated=2021-12-31T18:11:21, status=CREATED)
    ```

## <a name="sbp">2. Spring Boot Profiles</a>
Spring boot gives the option to the application to have profiles which are used to separate with components, services 
or just java beans will be initialised at boot time. According to microservices architectural guidelines, every 
microservice should do exactly one job and no more. Therefore, we separate the two jobs of this app into to different 
spring boot profiles as it is listed below. 

- producer
- consumer

You can check yourself for the seperated profiles in [application.yml](src/main/resources/application.yml) in the 
source code and then check the [docker-compose.yml](docker-compose.yml) for the use of the different profiles for the 
related containers.

### 2.1. Producer and Consumer for Kafka

As you can see in line 3 of [application.yml](src/main/resources/application.yml) file, if *SPRING_PROFILES_ACTIVE* is 
not defined, the default profiles that will be initialized on spring boot are both: producer, consumer.
While you have deployed kafka separately with [docker-compose-kafka-only.yml](docker-compose-kafka-only.yml), if you 
clean, package and run java jar with the following two lines, you will - by default - have started both profiles. 
```bash
  mvn clean package
  java -jar target/*.jar
```
or
```bash
  mvn spring-boot:run
```
### 2.2. Producer to Kafka

In case you want to deploy only producer spring boot profile, then we need the following two lines. The trick is to 
define the *spring.profiles.active* in the JVM parameters as producer.  
```bash
  mvn clean package
  java -Dspring.profiles.active=producer -jar target/*.jar
```
or you can use maven's spring-boot phase to pass the profiles parameter through *spring-boot.run.profiles* as it is 
used in the following command: 
```bash
  mvn spring-boot:run -Dspring-boot.run.profiles=producer
```

### 2.3. Consumer from Kafka

Likewise, to deploy only consumer spring boot profile, then we need the following two lines. The same trick for the JVM 
parameters for consumer.
```bash
  mvn clean package
  java -Dspring.profiles.active=consumer -jar target/*.jar
```
or likewise, use maven's spring-boot phase for consumer as we did for producer:
```bash
  mvn spring-boot:run -Dspring-boot.run.profiles=consumer
```


## 3. Config your kafka Docker Images on docker-compose file

There are three images that most GitHub's repositories are using for Kafka brokers in docker-compose file, as it is 
shown in the following yml examples:
- [Wurstmeister](https://github.com/wurstmeister/kafka-docker) images from 
[wurstmeister/kafka](https://hub.docker.com/r/wurstmeister/kafka) docker hub, 
- [Bitnami](https://github.com/bitnami/bitnami-docker-kafka) images from 
[bitnami/kafka](https://hub.docker.com/r/bitnami/kafka) docker hub.
- [Confluentinc](https://github.com/confluentinc/cp-docker-images) images from
  [confluentinc/cp-kafka](https://hub.docker.com/r/confluentinc/cp-kafka/) docker hub.

For this pilot, [Wurstmeister](https://github.com/wurstmeister/kafka-docker) images are currently utilized.

### 3.1. Wurstmeister Kafka

```yml
#sample from docker-compose.yml file
version: '3'
services:
  zookeeper:
    image: wurstmeister/zookeeper
    container_name: zookeeper
    ports:
      - '2181:2181'
  kafka:
    image: wurstmeister/kafka
    container_name: Kafka
    ports:
      - '9092:9092'
    environment:
      KAFKA_ADVERTISED_HOST_NAME: localhost
      KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
```
### 3.2. Bitnami Kafka

[comment]: <> (todo-geand: It has not be tested in dev or staging env yet)
```yml
#sample from docker-compose.yml file
version: "2"

services:
  zookeeper:
    image: docker.io/bitnami/zookeeper:3.7
    ports:
      - "2181:2181"
    volumes:
      - "zookeeper_data:/bitnami"
    environment:
      - ALLOW_ANONYMOUS_LOGIN=yes
  kafka:
    image: docker.io/bitnami/kafka:3
    ports:
      - "9092:9092"
    volumes:
      - "kafka_data:/bitnami"
    environment:
      - KAFKA_CFG_ZOOKEEPER_CONNECT=zookeeper:2181
      - ALLOW_PLAINTEXT_LISTENER=yes
    depends_on:
      - zookeeper

volumes:
  zookeeper_data:
    driver: local
  kafka_data:
    driver: local
```

### 3.3. Confluentinc  Kafka

Confluent and Kafka latest version are available at [confluentinc/cp-all-in-one](https://github.com/confluentinc/cp-all-in-one/tree/latest/cp-all-in-one) repo.

[comment]: <> (todo-geand: It has not be tested in dev or staging env yet)

```yml
#sample from docker-compose.yml file
version: '2'
services:
  zookeeper:
    image: confluentinc/cp-zookeeper:7.0.1
    hostname: zookeeper
    container_name: zookeeper
    ports:
      - "2181:2181"
    environment:
      ZOOKEEPER_CLIENT_PORT: 2181
      ZOOKEEPER_TICK_TIME: 2000

  broker:
    image: confluentinc/cp-server:7.0.1
    hostname: broker
    container_name: broker
    depends_on:
      - zookeeper
    ports:
      - "9092:9092"
      - "9101:9101"
    environment:
      KAFKA_BROKER_ID: 1
      KAFKA_ZOOKEEPER_CONNECT: 'zookeeper:2181'
      KAFKA_LISTENER_SECURITY_PROTOCOL_MAP: PLAINTEXT:PLAINTEXT,PLAINTEXT_HOST:PLAINTEXT
      KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://broker:29092,PLAINTEXT_HOST://localhost:9092
      KAFKA_METRIC_REPORTERS: io.confluent.metrics.reporter.ConfluentMetricsReporter
      KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1
      KAFKA_GROUP_INITIAL_REBALANCE_DELAY_MS: 0
      KAFKA_CONFLUENT_LICENSE_TOPIC_REPLICATION_FACTOR: 1
      KAFKA_CONFLUENT_BALANCER_TOPIC_REPLICATION_FACTOR: 1
      KAFKA_TRANSACTION_STATE_LOG_MIN_ISR: 1
      KAFKA_TRANSACTION_STATE_LOG_REPLICATION_FACTOR: 1
      KAFKA_JMX_PORT: 9101
      KAFKA_JMX_HOSTNAME: localhost
      KAFKA_CONFLUENT_SCHEMA_REGISTRY_URL: http://schema-registry:8081
      CONFLUENT_METRICS_REPORTER_BOOTSTRAP_SERVERS: broker:29092
      CONFLUENT_METRICS_REPORTER_TOPIC_REPLICAS: 1
      CONFLUENT_METRICS_ENABLE: 'true'
      CONFLUENT_SUPPORT_CUSTOMER_ID: 'anonymous'
#...
```

## 4. Models
The models that will transfer the data are the [Action](src/main/java/com/pilot/commons/Action.java), 
which will curry the [Sms](src/main/java/com/pilot/commons/Sms.java) enriched with 
[Status](src/main/java/com/pilot/commons/Status.java). The simplified POJO versions of these Models are following. 

### 4.1. Action Model

```java
    public class Action {
        String id;
        Sms sms;
        LocalDateTime created;
        LocalDateTime updated;
        String status;
    }
```

### 4.2. Sms Model

```java 
    public class Sms {
        String body;
        String sender;
        String receiver;
        private LocalDateTime timestamp;
    }
```
### 4.3. Status Emum

```java 
    public enum Status {
        CREATED,
        PENDING,
        INVALID,
        VALID,
        DELIVERED
    }
```
### 4.4. Web Request and Response bodies  
The web [SmsRequest](src/main/java/com/pilot/springbootkafkaproducerconsumer/web/SmsRequest.java) when a user will send 
and web [SmsResponse](src/main/java/com/pilot/springbootkafkaproducerconsumer/web/SmsResponse.java) that will be 
received are also available from [swagger-ui](http://localhost:9000/swagger-ui.html) as long the server (in producer's 
profile) is deployed.

## 5. OpenAPI Definition

In onder to check this app's API calls, which are controlled by *producer* profile, you can check 
[swagger-ui](http://localhost:9000/swagger-ui.html).

The [api-docs](http://localhost:9000/api-docs) are also available.

## 6. Read More
You might be interested to check the [Common HowTo's](docs/how-to.md).