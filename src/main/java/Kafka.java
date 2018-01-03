import java.util.Properties;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import kafka.serializer.StringEncoder;

/**
 * Created by btw on 2017/9/6.
 */
public class Kafka {

    public static void main(String[] args) throws Exception {
        Properties prop = new Properties();
        prop.put("zookeeper.connect", "10.10.56.138:2181");
        prop.put("metadata.broker.list", "10.10.56.138:9092");
        prop.put("serializer.class", StringEncoder.class.getName());
        Producer<String, String> producer = new Producer<String, String>(new ProducerConfig(prop));
        int i = 0;
        while (true) {
            producer.send(new KeyedMessage<String, String>("megfor", "msg:" + i++));
            Thread.sleep(1000);
        }
    }
}
