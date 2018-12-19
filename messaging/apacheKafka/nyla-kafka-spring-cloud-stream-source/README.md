# Kafka Spring Stream Cloud Source


	app register --name nyla-kafka --type source --uri file:///Projects/solutions/nyla/integration/dev/nyla-integration/messaging/apacheKafka/nyla-kafka-spring-cloud-stream-source/target/nyla-kafka-spring-cloud-stream-source-0.0.1-SNAPSHOT.jar
	
	app import --uri file:///Projects/solutions/nyla/integration/dev/nyla-integration/messaging/apacheKafka/nyla-kafka-spring-cloud-stream-source/target/nyla-kafka-spring-cloud-stream-source-0.0.1-SNAPSHOT.jar

## Starters

	app import --uri http://bit.ly/Darwin-SR3-stream-applications-rabbit-maven
	
## Stream

	stream create --definition "nyla-kafka | log" --name deleteLog
	
	stream deploy --name deleteLog --properties  app.nyla-kafka.BOOTSTRAP_SERVERS_CONFIG=localhost:9092,app.nyla-kafka.KAFKA_GROUP_ID=scdf