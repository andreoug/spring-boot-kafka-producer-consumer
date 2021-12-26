# Spring Boot with Kafka 

This is a pilot repo, where I implemented with latest spring boot 2.5.6 and Kafka broker.
The use case is the simplest example where a producer app sends an Object to kafka and a reducer app 
consumes the Object.


## 1. Getting Started
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
Follow the steps on section [1.2. Working on the Staging Environment](#wotse).

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
Follow the steps on section [1.3. Working on the Development Environment](#wotde).

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
      docker-composer up -d
    ```
2. Send a message to the app to produce and consume it as follows
    ```bash
      curl -X POST -F 'message=test1' http://localhost:9000/kafka/publish
    ```
3. Check the logs from producer through docker-compose logs

    ```bash
        $ docker-compose logs producer
        ...
        producer     | 2021-12-16 10:40:43.539  INFO 1 --- [nio-9000-exec-1] com.pilot.kafkac.Producer                : #~#: Producing message -> Message(id=59969dc0, sms=Sms(body=test1, timestamp=2021-12-16T10:40:43.538), created=2021-12-16T10:40:43.539, updated=2021-12-16T10:40:43.539, status=CREATED)
        producer     | 2021-12-16 10:40:43.601  INFO 1 --- [nio-9000-exec-1] o.a.k.clients.producer.ProducerConfig    : ProducerConfig values:
        ...
        producer     | 2021-12-16 10:40:43.976  INFO 1 --- [nio-9000-exec-1] o.a.kafka.common.utils.AppInfoParser     : Kafka version: 2.7.1
        producer     | 2021-12-16 10:40:43.979  INFO 1 --- [nio-9000-exec-1] o.a.kafka.common.utils.AppInfoParser     : Kafka commitId: 61dbce85d0d41457
        producer     | 2021-12-16 10:40:43.980  INFO 1 --- [nio-9000-exec-1] o.a.kafka.common.utils.AppInfoParser     : Kafka startTimeMs: 1639651243969
        producer     | 2021-12-16 10:40:44.645  INFO 1 --- [ad | producer-1] org.apache.kafka.clients.Metadata        : [Producer clientId=producer-1] Cluster ID: EK6fPBymTZaLS-e-JdoS4w
    ```

4. Check the logs from reducer to see the consumed message through docker-compose logs

    ```bash
        $ docker-compose logs consumer
        ...
        consumer     | 2021-12-16 10:40:40.436  INFO 1 --- [ntainer#0-0-C-1] o.a.k.c.c.internals.ConsumerCoordinator  : [Consumer clientId=consumer-group_id-1, groupId=group_id] Adding newly assigned partitions: messages-0
        consumer     | 2021-12-16 10:40:40.474  INFO 1 --- [ntainer#0-0-C-1] o.a.k.c.c.internals.ConsumerCoordinator  : [Consumer clientId=consumer-group_id-1, groupId=group_id] Found no committed offset for partition messages-0
        consumer     | 2021-12-16 10:40:40.506  INFO 1 --- [ntainer#0-0-C-1] o.a.k.c.c.internals.SubscriptionState    : [Consumer clientId=consumer-group_id-1, groupId=group_id] Resetting offset for partition messages-0 to position FetchPosition{offset=0, offsetEpoch=Optional.empty, currentLeader=LeaderAndEpoch{leader=Optional[kafka:9092 (id: 1001 rack: null)], epoch=0}}.
        consumer     | 2021-12-16 10:40:40.509  INFO 1 --- [ntainer#0-0-C-1] o.s.k.l.KafkaMessageListenerContainer    : group_id: partitions assigned: [messages-0]
        consumer     | 2021-12-16 10:40:45.071  INFO 1 --- [ntainer#0-0-C-1] com.pilot.kafkac.Consumer                : #~#: Consumed message -> Message(id=59969dc0, sms=Sms(body=test1, timestamp=2021-12-16T10:40:43), created=2021-12-16T10:40:43, updated=2021-12-16T10:40:43, status=CREATED)
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
  curl -X POST -F 'message=test1' http://localhost:9000/kafka/publish
```

5. Check you logs

    ```bash
        ...
        2021-12-16 13:12:56.989  INFO 47344 --- [nio-9000-exec-1] com.pilot.kafka.prodconc.Producer        : #~#: Producing message -> Message(id=30dce169, sms=Sms(body=test1, timestamp=2021-12-16T13:12:56.988), created=2021-12-16T13:12:56.988, updated=2021-12-16T13:12:56.988, status=CREATED)
        2021-12-16 13:12:56.993  INFO 47344 --- [nio-9000-exec-1] o.a.k.clients.producer.ProducerConfig    : ProducerConfig values:
        ...
        2021-12-16 13:12:57.009  INFO 47344 --- [nio-9000-exec-1] o.a.kafka.common.utils.AppInfoParser     : Kafka version: 2.7.1
        2021-12-16 13:12:57.009  INFO 47344 --- [nio-9000-exec-1] o.a.kafka.common.utils.AppInfoParser     : Kafka commitId: 61dbce85d0d41457
        2021-12-16 13:12:57.009  INFO 47344 --- [nio-9000-exec-1] o.a.kafka.common.utils.AppInfoParser     : Kafka startTimeMs: 1639653177008
        2021-12-16 13:12:57.021  INFO 47344 --- [ad | producer-1] org.apache.kafka.clients.Metadata        : [Producer clientId=producer-1] Cluster ID: kSxJW162RfiitJjeGfqr7A
        2021-12-16 13:12:57.110  INFO 47344 --- [ntainer#0-0-C-1] com.pilot.kafka.prodconc.Consumer        : #~#: Consumed message -> Message(id=30dce169, sms=Sms(body=test1, timestamp=2021-12-16T13:12:56), created=2021-12-16T13:12:56, updated=2021-12-16T13:12:56, status=CREATED)
    ```

## 2. Spring Boot Profiles
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
or you can use maven's spring-boot phase to pass the profiles's parameter through *spring-boot.run.profiles* as it is 
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

There are two images that most repositories are using for Kafka:
- [Wurstmeister](https://github.com/wurstmeister/kafka-docker) 's images from 
[wurstmeister/kafka](https://hub.docker.com/r/wurstmeister/kafka) docker hub, 
- [Bitnami](https://github.com/bitnami/bitnami-docker-kafka) 's images from 
[bitnami/kafka](https://hub.docker.com/r/bitnami/kafka) docker hub.


### 3.1. Wurstmeister Kafka

```yml
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

## 4. OpenAPI Definition

In onder to check this app's API calls, which are controlled by *producer* profile, you can check [swagger-ui](http://localhost:9000//swagger-ui.html).

The [api-docs](http://localhost:9000//api-docs) are also available.

## 5. Read More
You might be interested to check the [Common HowTo's](docs/how-to.md).