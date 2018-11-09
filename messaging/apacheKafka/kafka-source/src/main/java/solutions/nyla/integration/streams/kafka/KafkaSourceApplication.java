package solutions.nyla.integration.streams.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.core.MessageSource;
import org.springframework.messaging.support.MessageBuilder;

@SpringBootApplication
@EnableBinding(Source.class)
public class KafkaSourceApplication {

	@Bean
	@InboundChannelAdapter(
	  value = Source.OUTPUT, 
	  poller = @Poller(fixedDelay = "100", maxMessagesPerPoll = "1")
	)
	public MessageSource<String> timeMessageSource() {
	    return () -> MessageBuilder.withPayload("hello").build();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(KafkaSourceApplication.class, args);
	}
}
