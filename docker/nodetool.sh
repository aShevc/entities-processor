#!/bin/bash

sudo docker exec -it docker_cassandra_1 nodetool "$@"