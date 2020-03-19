#!/bin/bash

until cqlsh -f /opt/metrics-processor/schema.cql; do
  echo "cqlsh: Cassandra is unavailable - retry later"
  sleep 2
done &

exec /docker-entrypoint.sh "$@"
