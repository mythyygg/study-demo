package producer;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;


/**
 * 
* Title: KafkaProducerTest
* Description: 
* kafka 生产者demo
* Version:1.0.0  
* @author pancm
* @date 2018年1月26日
 */
public class KafkaProducerTest implements Runnable {

	private final KafkaProducer<String, String> producer;
	private final String topic;

	
	public KafkaProducerTest(String topicName) {
		Properties props = new Properties();
		props.put("bootstrap.servers", "localhost:9092");
		//acks=0：如果设置为0，生产者不会等待kafka的响应。
		//acks=1：这个配置意味着kafka会把这条消息写到本地日志文件中，但是不会等待集群中其他机器的成功响应。
		//acks=all：这个配置意味着leader会等待所有的follower同步完成。这个确保消息不会丢失，除非kafka集群中所有机器挂掉。这是最强的可用性保证。
		props.put("acks", "all");
		//配置为大于0的值的话，客户端会在消息发送失败时重新发送。
		props.put("retries", 0);
		//当多条消息需要发送到同一个分区时，生产者会尝试合并网络请求。这会提高client和生产者的效率
		props.put("batch.size", 16384);
		props.put("key.serializer", StringSerializer.class.getName());
		props.put("value.serializer", StringSerializer.class.getName());
		this.producer = new KafkaProducer<>(props);
		this.topic = topicName;
	}

	@Override
	public void run() {
		int messageNo = 1;
		try {
			for(;;) {

				String messageStr="你好，这是第"+messageNo+"条数据."+
						"BUG org.apache.kafka.cod sensor with name node-0.bytes0 [kafka-producer-network-thread | producer-1] DEBUG org.apache.kafka.common.metrics.Metrics - Removed sensor with name node-0.bytes0 [kafka-producer-network-thread | producer-1] DEBUG org.apache.kafka.common.metrics.Metrics - Removed sensor with name node-0.bytes0 [kafka-producer-network-thread | producer-1] DEBUG org.apache.kafka.common.metrics.Metrics - Removed sensor with name node-0.bytes0 [kafka-producer-network-thread | producer-1] DEBUG org.apache.kafka.common.metrics.Metrics - Removed sensor with name node-0.bytes | producer-1] DEBUG org.apache.kafka.common.metrics.Metrics - Removed sensor with name node-0.bytes0 [kafka-producer-network-thread | producer-1] DEBUG org.apache.kafka.common.metrics.Metrics - Removed sensor with name node-0.bytes0 [kafka-producer-network-thread | producer-1] DEBUG org.apache.kafka.common.metrics.Metrics - Removed sensor with name node-0.bytes";
				producer.send(new ProducerRecord<String, String>(topic, "Message", messageStr));
				if(messageNo%10==0){
					System.out.println("发送的信息:" + messageStr);
				}
				if(messageNo%100==0){
					System.out.println("成功发送了"+messageNo+"条");
					break;
				}
				messageNo++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			producer.close();
		}
	}

	public static void main(String args[]) {
		KafkaProducerTest test = new KafkaProducerTest("test");
		Thread thread = new Thread(test);
		thread.start();
	}
}