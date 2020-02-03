#!/usr/bin/env bash

sudo docker exec -it docker_cassandra_1 cqlsh -f /opt/metrics-processor/cassandra/schema.cql