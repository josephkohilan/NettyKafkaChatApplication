package com.chat.queue.dao;

public interface KafkaQueueDAO {
	
	public void writeQueue(String message);

}
