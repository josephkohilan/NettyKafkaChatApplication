/**  
* KafkaQueueDAOImpl.java - This class hits the consumer
* @author  Joseph Kohilan
* @version 1.0 
* @see KafkaQueueDAO, KafkaQueueDAOImpl 
*/

package com.chat.queue.dao;

import java.util.Properties;
import java.util.logging.Logger;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class KafkaQueueDAOImpl implements KafkaQueueDAO{
	
	Logger logger = Logger.getLogger(KafkaQueueDAOImpl.class.getName());
	
	/*
	 * This method contains the kafka queue details and it puts the message into the topic of the particular queue
	 * @param String message
	 */
	public void writeQueue(String message){
		String topicName = "kafkaTopic";
		Properties props = new Properties();
		props.put("bootstrap.servers", "localhost:9092");
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

		Producer<String, String> producer = new KafkaProducer<String, String>(props);
		producer.send(new ProducerRecord<String, String>(topicName, message));
		producer.close();
	}
	
}