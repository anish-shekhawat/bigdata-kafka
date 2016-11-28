package edu.sjsu.cs185; 

import com.google.common.io.Resources;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.distribution.AbstractRealDistribution;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Producer {
	public static void main(String[] args) throws IOException {
		// error-check the command line
		if (args.length != 2) {
			System.err.println("usage: Producer <topic-name> <pump-id>");
			System.exit(1);
		}
		// parse the command line 
		String topic = args[0];
		int pumpId = Integer.parseInt(args[1]);
}
