package solutions.nyla.apacheKafka;

import java.time.Duration;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Properties;
import java.util.Queue;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import nyla.solutions.core.util.Config;

public class KafkaQueue<E>  extends LinkedList<E>  implements Queue<E>, Runnable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3824535058258178886L;

	KafkaQueue(String topic)
	{
		this.topic = topic;
	}
	
	@SuppressWarnings({ "unchecked" })
	@Override
	public void run()
	{

 
    	 Properties props = new Properties();
         props.put("bootstrap.servers", Config.getProperty("BOOTSTRAP_SERVERS_CONFIG"));
         props.put("group.id", Config.getProperty("KAFKA_GROUP_ID"));
         props.put("enable.auto.commit", "true");
         props.put("auto.commit.interval.ms", "1000");
         //props.put("auto.offset.reset", "earliest");
         props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
         props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
         try(KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props))
         {
             consumer.subscribe(Collections.singleton(this.topic));
             //consumer.seekToBeginning(consumer.assignment());
             
             System.out.println("START looking for messages for topic:"+this.topic);
             while (true) {
            	 
                 try
				 {
					ConsumerRecords<String, String> records = consumer.poll(Duration.ZERO);
					 for (ConsumerRecord<String, String> record : records)
					 {
					     System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
					     
					     this.add((E)record.value());
					   
					 }
					 Thread.sleep(kakfaQueueSleepMs);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
                 
             }
         }
	}//------------------------------------------------
	
	private long kakfaQueueSleepMs = Config.getPropertyLong("kakfaQueueSleepMs", 5);
	private final String topic;
	
	
}
