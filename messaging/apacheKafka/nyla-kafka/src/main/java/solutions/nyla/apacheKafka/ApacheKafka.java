package solutions.nyla.apacheKafka;

import java.util.Properties;
import java.util.concurrent.BlockingQueue;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import nyla.solutions.core.util.Config;
public class ApacheKafka
{
	/**
	 * 
	 * @param bootStrapServersConfig the boot strap servers
	 */
	private ApacheKafka(String bootStrapServersConfig,String groupdId)
	{
		if (bootStrapServersConfig == null || bootStrapServersConfig.length() == 0)
			throw new IllegalArgumentException("bootStrapServersConfig is required");
		
		this.bootStrapServersConfig =bootStrapServersConfig;
		this.groupdId = groupdId;
	}//------------------------------------------------
	
	public static ApacheKafka connect()
	{
		return connect(Config.getProperty("BOOTSTRAP_SERVERS_CONFIG"),
					   Config.getProperty("KAFKA_GROUP_ID"));
	}//------------------------------------------------
	public static ApacheKafka connect(String bootStrapServersConfig, String groupId)
	{
		return new ApacheKafka(bootStrapServersConfig,groupId);
	}//------------------------------------------------
	public <K,V> void push(String topic, K key,V value)
	{
		Properties props = new Properties();
		 props.put("bootstrap.servers", this.bootStrapServersConfig);
		 props.put("acks", "all");
		 props.put("retries", 0);
		 props.put("batch.size", 16384);
		 props.put("linger.ms", 1);
		 props.put("buffer.memory", 33554432);
		 props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		 props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

		 try(Producer<K, V> producer = new KafkaProducer<>(props))
		 {
			  producer.send(new ProducerRecord<K, V>(topic, key, value));
		 }
		 		
	}//------------------------------------------------
	public <E> BlockingQueue<E> queue(String topic)
	{
		KafkaQueue<E> q = new KafkaQueue<>(topic,this.bootStrapServersConfig, this.groupdId);
		new Thread(q).start();
		
		return q;
	}//------------------------------------------------
	private final String bootStrapServersConfig;
	private final String groupdId;
}
