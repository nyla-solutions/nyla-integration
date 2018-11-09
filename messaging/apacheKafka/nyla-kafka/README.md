# Nyla Kafka

Very basic wrapper for Apache Kafka

Example code

		Queue<String> q= ApacheKafka.connect().queue("test");
		
		ApacheKafka.connect().push("test", "hello", "world");
		
		Thread.sleep(5000);
		
		String out = q.poll();
		assertNotNull(out);
		System.out.println("out:"+out);