FROM cassandra:3.11.4

ENV LOCAL_JMX no

RUN sed -i 's/JVM_OPTS="$JVM_OPTS -Dcom.sun.management.jmxremote.authenticate=true"/JVM_OPTS="$JVM_OPTS -Dcom.sun.management.jmxremote.authenticate=false"/g' /etc/cassandra/cassandra-env.sh
RUN echo 'JVM_OPTS="$JVM_OPTS -javaagent:'/prometheus/jmx_prometheus_javaagent-0.12.0.jar=7070:/prometheus/prometheus.yml'"' | tee -a /etc/cassandra/cassandra-env.sh