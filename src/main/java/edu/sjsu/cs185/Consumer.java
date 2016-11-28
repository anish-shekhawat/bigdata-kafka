package edu.sjsu.cs185; 

import com.google.common.io.Resources;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;
import java.util.Random;

public class Consumer {
	public static void main(String[] args) throws IOException {
		// error-check the command line
		if (args.length !=3 ) {
			System.err.println("usage: Consumer <topic> <threshold> <file>");
			System.exit(1);
		}
        
		// parse the command-line
        String topic = args[0];
		Double threshold = Double.parseDouble(args[1]);
		String file = args[2];

		// setup consumer 
		KafkaConsumer<String, String> consumer;
		try (InputStream props = Resources.getResource("consumer.props").openStream()) {
			Properties properties = new Properties();
			properties.load(props);
			if (properties.getProperty("group.id") == null) {
				properties.setProperty("group.id", "group-" + new Random().nextInt(100000));
			}
			consumer = new KafkaConsumer<>(properties);
			consumer.subscribe(Arrays.asList(topic));
			int timeouts = 0;
			
			while (true) {
				ConsumerRecords<String, String> records = consumer.poll(200);
				if (records.count() == 0) {
					timeouts++;
				} 
				else {
					System.out.printf("Got %d records after %d timeouts\n", records.count(), timeouts);
					timeouts = 0;
				}
			
				for (ConsumerRecord<String, String> record : records) {
					// pull out the vibration delta from record
					String[] tokens = record.value().split(",");
					
					// determine if it's greater than the threshold
					if (Double.parseDouble(tokens[2]) > threshold)
					{
						// if it is greater, than write that record to the file
						Files.append(record.value() + "\n", output, Charsets.UTF_8);
					} 
				}
			}
			
		}

	}
}
