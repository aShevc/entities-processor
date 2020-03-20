#!/usr/bin/env bash

set -ex

GRAFANA_USER=admin
GRAFANA_PASS=admin
GRAFANA_API_URL=172.21.0.6
GRAFANA_API_PORT=3000

curl -H 'Content-Type: application/json' \
  -X POST http://${GRAFANA_USER}:${GRAFANA_PASS}@${GRAFANA_API_URL}:${GRAFANA_API_PORT}/api/datasources \
  --data-binary '{
                "name":"prometheus",
                "type":"prometheus",
                "isDefault":true,
                "url":"http://prometheus:9090",
                "access":"proxy"}'
