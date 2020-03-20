CREATE KEYSPACE entities_processor WITH REPLICATION = {'class':'SimpleStrategy', 'replication_factor': 1};

USE entities_processor;

CREATE TABLE temperature_metrics (
	device_id TEXT,
	date TEXT,
	time TIMESTAMP,
	temperature INT,
	PRIMARY KEY ((device_id), date, time)
) WITH CLUSTERING ORDER BY (date DESC, time DESC);

// Schema created to conform with the query: get metrics for device per day. All of them, or for particular time period