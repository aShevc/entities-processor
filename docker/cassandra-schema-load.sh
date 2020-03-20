#!/usr/bin/env bash

docker exec -it docker_cassandra_1 cqlsh -f /opt/entities-processor/cassandra/schema.cql