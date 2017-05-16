/**  
* ChatServerHandler.java - This class handles the incoming message received from the Server 
* @author  Joseph Kohilan
* @version 1.0 
* @see ChatServer, ChatServerInitializer 
*/

package com.chat.server;

import java.util.logging.Logger;

import org.json.JSONObject;

import com.chat.queue.dao.KafkaQueueDAOImpl;
import com.chat.utility.ChatConstants;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundMessageHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;

public class ChatServerHandler extends ChannelInboundMessageHandlerAdapter<String> {

	public static final ChannelGroup channels = new DefaultChannelGroup();
	
	Logger logger = Logger.getLogger(ChatServerHandler.class.getName());
	
	/*
	 * Handles the new client
	 * @param ChannelHandlerContext ctx
	 * @throws Exception
	 */
	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		Channel incomingChannel = ctx.channel();
		for (Channel channel : channels) {
			channel.write(incomingChannel.remoteAddress() + ChatConstants.JOINT_MESSAGE);
		}
		channels.add(ctx.channel());
		System.out.println(ctx.channel().remoteAddress() + ChatConstants.SERVER_CHANNEL_ADDED_MESSAGE);
	}

	/*
	 * Handles the client who exit
	 * @param ChannelHandlerContext ctx
	 * @throws Exception
	 */
	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		Channel incomingChannel = ctx.channel();
		for (Channel channel : channels) {
			channel.write(incomingChannel.remoteAddress() + ChatConstants.LEFT_MESSAGE);
		}
		channels.remove(ctx.channel());
		System.out.println(ctx.channel().remoteAddress() + ChatConstants.SERVER_CHANNEL_REMOVED_MESSAGE);
	}
	
	/*
	 * This method is invoked when a new message is received. It is overridden to handle the incoming message.
	 * Here it just prints the message and also sends message to queue
	 * @param SocketChannel channel
	 * @throws Exception
	 */
	@Override
	public void messageReceived(ChannelHandlerContext arg0, String message) throws Exception {
		Channel incomingChannel = arg0.channel();
		JSONObject jsonObject = new JSONObject(message);
		System.out.println(message);
		for (Channel channel : channels) {
			if (channel != incomingChannel) {
				channel.write(ChatConstants.OPEN_SQUARE_BRACKET + incomingChannel.remoteAddress()
						+ ChatConstants.CLOSE_SQUARE_BRACKET + jsonObject.getString(ChatConstants.JSON_MESSAGE_KEY)
						+ ChatConstants.NEXT_LINE);
			}
		}
		KafkaQueueDAOImpl kafkaQueueClass = new KafkaQueueDAOImpl();
		kafkaQueueClass.writeQueue(message);
	}

}
