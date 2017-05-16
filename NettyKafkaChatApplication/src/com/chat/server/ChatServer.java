/**  
* ChatServer.java - a simple class for demonstrating netty server-client chat application. 
* Running this class creates a server
* @author  Joseph Kohilan
* @version 1.0 
* @see ChatServerInitializer, ChatServerHandler 
*/
package com.chat.server;

import java.util.logging.Logger;

import com.chat.utility.ChatConstants;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class ChatServer {

	private final int port;
	
	Logger logger = Logger.getLogger(ChatServer.class.getName());

	/*
	 * Sets port number in which the server is hosted
	 */
	public static void main(String[] args) {
		new ChatServer(8000).startServer();
	}
	
	/*
	 * constructor which assigns port number
	 */
	public ChatServer(int port) {
		this.port = port;
	}
	
	/*
	 * Starts the server,
	 * Handles and passes messages to client
	 * Convert message to JSONObject
	 * Sends the JSONObject to kafka queue
	 */
	public void startServer() {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			System.out.println(ChatConstants.SERVER_RUNNING_MESSAGE);
			ServerBootstrap bootstrap = new ServerBootstrap().group(bossGroup, workerGroup)
					.channel(NioServerSocketChannel.class).childHandler(new ChatServerInitializer());

			bootstrap.bind(port).sync().channel().closeFuture().sync();
		} catch (Exception e) {
			logger.info("Error occured with chat server");
			System.out.println(ChatConstants.SERVER_STOPPED_MESSAGE);
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
			System.out.println(ChatConstants.SERVER_STOPPED_MESSAGE);
		}
	}
	
}
