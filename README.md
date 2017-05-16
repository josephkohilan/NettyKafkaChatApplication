# NettyKafkaChatApplication
NettyKafkaChatApplication is a netty server- client chat application in which the netty server queues the message in Kafka queue

To run this application,

1. Download Kafka Queue and set to its default settings. Create a topic "kafkaTopic".
2. Run the com.chat.server.ChatServer.java to start the Server.
3. Run the com.chat.client.ChatClient.java to create the Client.
  note: multiple clients can be created by running the com.chat.client.ChatClient.java multiple times.
4. Chat among the clients using console.
5. The server drops the chat message in KafkaQueue with message and date time in JSON format.
