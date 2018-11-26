package solutions.nyla.integration.streams.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;

@SpringBootApplication
@EnableBinding(Source.class)
public class KafkaSourceApplication {

	
	
	public static void main(String[] args) {
		SpringApplication.run(KafkaSourceApplication.class, args);
	}
}
