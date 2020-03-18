FROM prom/prometheus:v2.16.0

# giving us the ability to reload config on the fly
CMD [ "--config.file=/etc/prometheus/prometheus.yml", \
             "--storage.tsdb.path=/prometheus", \
             "--web.console.libraries=/usr/share/prometheus/console_libraries", \
             "--web.console.templates=/usr/share/prometheus/consoles", \
             "--web.enable-lifecycle" ]
