# Kafka Spring Stream Cloud Source


	app import --uri http://bit.ly/Darwin-SR3-stream-applications-rabbit-maven
	
	app register --name kafka --type source --uri file:///Projects/solutions/nyla/integration/dev/nyla-integration/messaging/apacheKafka/nyla-kafka-spring-cloud-stream-source/target/nyla-kafka-spring-cloud-stream-source-0.0.1-SNAPSHOT.jar


	stream create --definition "kafka --boot-strap-servers-config=localhost:9092 --group-id=scdf | log" --name deleteLog

	stream deploy --name deleteLog --properties  app.kafka.spring.cloud.stream.defaultBinder=rabbit1

## Starters

	app import --uri http://bit.ly/Darwin-SR3-stream-applications-rabbit-maven
	