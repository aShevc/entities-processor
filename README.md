# Entity Processor

## Overview and purpose
Entity Processor is a project developed for scaffolding JVM based applications that are built on top of distributed
infrastructure. More specifically, the project can be used as a templating tool for building and testing out data
pipelines. This tool may be used to accomplish the following objectives:

- Estimation of the system throughput for various technology stack options and creating comprehensive comparisons
between different tools
- Work on high level optimization of data processing techniques that are specific for the developed applications (
optimizing DB queries, system configuration for better performance, etc.)
- Estimate reliability of the setup by monitoring the components that are to be used with developed system with the
expected load

## Pre-requisites

Make sure to have 8 version of `java` as well as `sbt` installed.

## Build & run

In order to build the project run:

```
sbt compile
```

In order to run one of the [example apps](#apps), run:

```
sbt apps/run
```

Then, in the interactive console, choose one of the main classes to run, or run the chosen app like this:

```
sbt "apps/runMain org.entityproc.apps.LogEntityGeneratorApp"
```

## Concepts

Logically, the project is divided to `core` module and `apps` module, dedicated for layering out the resulting
applications. Other modules are created per infrastructure tool that you want to be able to use within this project.
Currently, the project has `cassandra` and `kafka` modules implemented. 

### Core
Core module contains application's `key entity` and `entity generator` that can be used as an entry point for data
pipelining.

#### Key Entity
This is application's messaging unit that is passed along and processed via various components of the system. It is
initially implemented as an `Avro` message, so it can be used in it's bytecode representation across various
tools.

In the example presented in this project, the key entity is presented as a metric, gathered from the IoT device,
reporting device temperature and it looks like this:

```
{"namespace": "org.entityproc.entity",
 "type": "record",
 "name": "Entity",
 "fields": [
     {"name": "device_id", "type": "string"},
     {"name": "temperature",  "type": "int"},
     {"name": "timestamp", "type": "long"}
 ]
}
```

One may customize project's key entity by modifying its Avro representation here: `core/src/main/avro/entity.avsc`
and generating new Java sources like so:

```
./generate-avro.sh
```

Note, that besides generating new key entities themselves, one will also need to modify the entity generator as well as
writers and listeners that one plans on using. Make sure that not only the module's codebase will need to change, but
one need to also change supporting resource files. For example, in `cassandra` module make sure to modify default CQL
schema in `casssandra/src/main/resources/cassandra/schema.cql` 

#### Entity Generator
Entity generator serves as an entry point for creating data pipelines and is responsible for generating stream of 
key entity messages and pushing it via the provided [writer](#writers).

Entity generator will work in two modes:

- Generating messages as fast as possible
- Generate messages with given per second rate 

Generator options can be configured via appropriate configurations:
```
  // set to "AFAP" for as fast as possible mode
  // set to "with-rate" for generating entities with the specific rate
  generator-mode = "AFAP"
  // Specifies rate per second for rated mode
  sample-rate = 100
  // Amounts of seconds during which to generate entities
  duration = 3600
  // Amounts of entities to generate in the AFAP mode 
  entities-amount = 100000000
  // For each new message, default entity generator will write a new device id until devices-amount is reached.
  // After then, it will increase timestamp field by one second and start from the first posted device id again
  devices-amount = 10000
```

### Writers
Writers are responsible for pushing key entity messages into any external infrastructure (database, message queue, etc.).
In order to create a custom writer to be used with the entity generator, create a Scala trait that extends `EntitiesWriter`
interface. One may see `LogWriter` example provided in the `core` module.

Also in case if custom properties need to be provided for a writer, one will need to implement its custom config
component by creating a trait that extends base `Config` trait. Then, the writer trait will also extend its Config
component and therefore will be allowed to use its methods to get required properties:

```

trait SomeWriterConfig extends Config {
...
def getSomeProperty = ...


trait SomeWriter extends EntitiesWriter with SomeWriterConfig {
...
 getSomeProperty

``` 

See `LogWriterConfig` and 
`LogWriter` in the `core` module for an example.

### Listeners
Listeners are responsible for consuming key entity messages from external infrastructure pieces. In order to create a 
custom listener that can output consumed messages into another external system, the listener declaration should be
accompanied with a `EntitiesWriter` trait *self type* like so:
 
```
...
   trait SomeListener {
     this: EntitiesWriter =>
...
``` 
 
 It means that the resulting object will need to extend this trait
as well. One may check out the listener implementation example in the provided `kafka` module. The benefits of using
self-types construct are getting evident when creating resulting applications. This process described in details
in the next section.

Also in case if custom properties need to be provided for a listener, one will need to implement its custom config
component by creating a trait that extends base `Config` trait. Then, the listener trait will also extend its Config
component and therefore will be allowed to use its methods to get required properties.  See `LogWriterConfig` and 
`LogWriter` in the `core` module for an example.

```

trait SomeListenerConfig extends Config {
...
def getSomeProperty = ...


trait SomeListener extends SomeListenerConfig {
  this: EntitiesWriter =>
...
 getSomeProperty

```

### Apps
 
#### Implementation and usage
In order to create an application that may use implemented entity generator as well as writer and listener components,
one needs to implement an app as the Scala application (by extending `App` trait), and within it construct a Scala
object consisting of the desired components. For example, consider `KafkaEntitiesGeneratorApp` provided in the `example`
module:

```
  ...
  object KMGApp extends KamonApp with KafkaEntitiesWriter with EntitiesGenerator
  ...
```
Let's consider used components here.

- At first we are using `KamonApp` trait, which allows us to add [monitoring](#monitoring) functionality for our
application
- Then we are extending `KafkaEntitiesWriter` stating that we want to output messages into Kafka as an external system
- And we want to generate messages ourselves, therefore `EntitiesGenerator` trait will be used. If we were to consume
entity messages from another application, for example, another Kafka topic, we would have used `KafkaEntitiesListener`
there instead.

From here, in order to launch an application, there are two options.
In case if messages are originated from EntitiesGenerator, call

```
app.generate()
```

otherwise, call an appropriate method on the listener:

```
app.listen()
```

#### Configuration and configuration files

Default configurations of the applications may be found in `apps/src/main/resources/reference.conf` file.
Additionally, there is an ability to provide a custom file for overriding default configuration by adding the following
to the resulting app object's body:

```
...
    override def getCustomConfigFile: Option[String] = Some("custom-file")
...
``` 

that way, custom configuration file `custom-file.conf` is going to be used to override default properties.

One may also customize configuration prefix so that application-specific properties are separated in a specific separate
section of the config file. This can also be done by modifying app's object body:

```
    override def getConfigPrefix: String = "my-app"
```

## Monitoring
In order to measure and visualize application and infrastructure metrics the following suite of tools is used:

- Prometheus. Serves as the data storage and the scraping agent responsible for pulling metrics from external systems:
applications and infrastructure services. In order to be scraped by Prometheus, each of the apps and services have
a dedicated endpoint with metrics exposed for scraping.
- Grafana. Connects to Prometheus data source and utilizes it for metrics visualization.

If Docker environment is utilized in order to gather the metrics, the Prometheus is already set up to receive all the
required metrics. After Docker image of Grafana up and running, run the following in order to upload visualization
dashboards:

```
./grafana/bin/create-data-sources.sh
./grafana/bin/upload-dashboards.sh
```

After dashboards are uploaded, the following dashboard will appear on your Grafana installation:
- Entity Processor Dashboard - shows per second message throughput rate of each implemented listener and writer allowing
to estimate operational throughput of the applications
- Four Cassandra-related dashboards (originated from [here](https://github.com/thelastpickle/docker-cassandra-bootstrap)):
    - Cassandra Overview - general overview of operations
    - Cassandra Read Path - overview of read operations
    - Cassandra Write Path -  overview of write operations
    - Cassandra Client Connections - client connections overview
- Kafka overview dashboard (originated from [here](https://grafana.com/grafana/dashboards/721))

### Configuration
There are several places where Monitoring process can be configured.

- Prometheus service config can be found at the `prometheus/config/prometheus.yml` directory.
- Prometheus config for apps, meaning setting for their respective endpoints, can be found and modified in each of the
applications config file.
- Prometheus config for Kafka and Cassandra services can be found at the `cassandra/config/prometheus.yml` and 
`kafka/config/prometheus.yml` files. Metrics port are configured for Kafka in the `KAFKA_OPTS` variable of the Docker
Compose, and for Cassandra in its `docker/cassandra/Dockerfile`.

One may also re-configure Prometheus config yml file and apply changes when application is running by executing:

```
./docker/reload-prometheus-config.sh
```

## Docker
The project ships with the Docker compose file that can start up the following containers:

**Note:** make sure you have Docker and Docker Compose installed on your machine first

- Zookeeper 
    - IP 172.21.0.2
- Kafka
    - Starts with metrics endpoint for Prometheus at 7071
    - IP 172.21.0.3
- Schema Registry 
    - Required for Avro schema validation for Kafka messages
    - IP 172.21.0.4
- Cassandra
    - Starts with JMX metrics available via 7199 and metrics endpoint for Prometheus at 7070 port
    - Key entity schema loads on image startup, additionally can be loaded via `cassandra-schema-load.sh` script
    - One may also use `cqlsh.sh` and `nodetool.sh` to access corresponding tools on the container 
    - IP 172.21.0.5    
- Grafana
    - IP 172.21.0.6
- Prometheus
    - Configured at start up to scrap Kafka and Cassandra services as well as all the apps of entity processor project
    - IP 172.21.0.7
    
In order to start up the whole stack run from `docker` directory:

```
docker-compose up -d
```

In order to start any particular service, or several services, run:

```
docker-compose up -d <service1> <service2>
```

Note, that `kafka`, `cassandra`, `prometheus` and `grafana` services have data saved as volumes on the machine inside 
`docker/data` directory. Make sure to clean up this directory if you want to start from the clean slate.