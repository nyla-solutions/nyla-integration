package solutions.nyla.apacheKafka;

import java.util.Properties;
import java.util.Queue;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import nyla.solutions.core.util.Config;
public class ApacheKafka
{
	private ApacheKafka()
	{
	}//------------------------------------------------
	
	public static ApacheKafka connect()
	{
		synchronized (ApacheKafka.class)
		{
			if(instance != null)
				return instance;
			
			return new ApacheKafka();
		}
	}//------------------------------------------------
	public <K,V> void push(String topic, K key,V value)
	{
		Properties props = new Properties();
		 props.put("bootstrap.servers", Config.getProperty("BOOTSTRAP_SERVERS_CONFIG"));
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
			  //producer.close();
		 }
		 		
	}
	public <E> Queue<E> queue(String topic)
	{
		KafkaQueue<E> q = new KafkaQueue<>(topic);
		new Thread(q).start();
		
		return q;
	}//------------------------------------------------
	
	private static ApacheKafka instance;
}
