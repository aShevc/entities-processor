#!/bin/bash

docker exec -it docker_cassandra_1 nodetool "$@"