package solutions.nyla.integration.streams.kafka;

import java.util.concurrent.BlockingQueue;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.core.MessageSource;
import org.springframework.messaging.support.MessageBuilder;

import solutions.nyla.apacheKafka.ApacheKafka;

@Configuration
public class KakfaSourceConfig
{
	String topic;
	
	@Autowired
	ApacheKafka apacheKafka;
	
	@Resource
	BlockingQueue<String> queue;
	
	@Bean
	ApacheKafka kakfa()
	{
		return ApacheKafka.connect();
	}//------------------------------------------------
	
	@Bean("queue")
	BlockingQueue<String> queue(ApacheKafka apacheKafka, Environment env)
	{
		topic = env.getRequiredProperty("topic");
		
		return apacheKafka.queue(topic);
	}//------------------------------------------------
	
	@Bean
	@InboundChannelAdapter(
	  value = Source.OUTPUT, 
	  poller = @Poller(fixedDelay = "5", maxMessagesPerPoll = "1")
	)
	public MessageSource<String> timeMessageSource() 
	{
	    return () -> { 
	    	String msg = this.queue.poll();
	    	
	    	if(msg == null)
	    		return null;
	    	
	    	System.out.println("topic:"+topic+" message:"+msg);
	    	
	    	return MessageBuilder.withPayload(msg).build();
	    };
	}//------------------------------------------------

}
