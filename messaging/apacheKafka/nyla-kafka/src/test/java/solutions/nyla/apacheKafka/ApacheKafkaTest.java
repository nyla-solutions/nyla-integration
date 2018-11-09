package solutions.nyla.apacheKafka;

import static org.junit.Assert.*;

import java.util.Queue;

import org.junit.Test;

public class ApacheKafkaTest
{

	@Test
	public void testPush()
	throws Exception
	{
		Queue<String> q= ApacheKafka.connect().queue("beacon");
		
		ApacheKafka.connect().push("beacon", "hello", "world");
		
		Thread.sleep(5000);
		
		String out = q.poll();
		assertNotNull(out);
		System.out.println("out:"+out);
		
		
	}

}
