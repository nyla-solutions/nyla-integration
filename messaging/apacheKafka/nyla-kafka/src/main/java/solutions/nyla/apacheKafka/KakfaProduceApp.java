package solutions.nyla.apacheKafka;

import java.util.Properties;
import java.util.concurrent.CountDownLatch;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;

/**
 * Hello world!
 *
 */
public class KakfaProduceApp 
{
    public static void main( String[] args )
    {
    	Properties props = new Properties();
    	props.put(StreamsConfig.APPLICATION_ID_CONFIG, "streams-pipe");
    	props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092"); 
    	props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
    	props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
    	
    	
    	final StreamsBuilder builder = new StreamsBuilder();
    	
    	String topic = "test";
    	KStream<String, String> source = builder.stream(topic);
    
    	source.foreach((x,y) -> System.out.println(x+":"+y));
    	final Topology topology = builder.build();
    	
    	System.out.println(topology.describe());
    	
    	final KafkaStreams streams = new KafkaStreams(topology, props);
        final CountDownLatch latch = new CountDownLatch(1);
        
        Runtime.getRuntime().addShutdownHook(new Thread("streams-shutdown-hook") {
            @Override
            public void run() {
                streams.close();
                latch.countDown();
            }
        });
 
        try {
            streams.start();
            latch.await();
        } catch (Throwable e) {
            System.exit(1);
        }
        System.exit(0);
    }
}
