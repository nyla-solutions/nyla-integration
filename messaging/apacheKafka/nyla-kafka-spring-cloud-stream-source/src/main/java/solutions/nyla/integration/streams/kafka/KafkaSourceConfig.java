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
public class KafkaSourceConfig
{
	private String topic;
	
	@Autowired
	ApacheKafka apacheKafka;
	
	@Resource
	BlockingQueue<String> queue;
	
	@Bean
	ApacheKafka kakfa(KakfaSourceProperties properties)
	{
		return ApacheKafka.connect(
		properties.getBootStrapServersConfig(),
		properties.getGroupId());
		
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
	    	try
			{
	    		System.out.println(this.getClass().getName()+": Getting records");
	    		
				String msg = this.queue.take();
				
				if(msg == null)
					return null;
				
				System.out.println("topic:"+topic+" message:"+msg);
				
				return MessageBuilder.withPayload(msg).build();
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
				throw new RuntimeException(e);
			}
	    };
	}//------------------------------------------------

}
