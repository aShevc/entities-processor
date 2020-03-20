#!/bin/bash

until cqlsh -f /opt/entities-processor/cassandra/schema.cql; do
  echo "cqlsh: Cassandra is unavailable - retry later"
  sleep 2
done &

exec /docker-entrypoint.sh "$@"
