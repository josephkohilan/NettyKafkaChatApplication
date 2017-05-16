/**  
* ChatClientInitializer.java - This class initializes the properties of Channel pipeline
* @author  Joseph Kohilan
* @version 1.0 
* @see ChatClient, ChatClientHandler
*/ 

package com.chat.client;

import java.util.logging.Logger;

import com.chat.utility.ChatConstants;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class ChatClientInitializer extends ChannelInitializer<SocketChannel> {
	
	Logger logger = Logger.getLogger(ChatClientHandler.class.getName());
	
	/*
	 * initChannel initializes the channel pipeline properties like framer, encoder, decoder, handler
	 * @param SocketChannel channel
	 * @throws Exception
	 */
	@Override
	protected void initChannel(SocketChannel channel) throws Exception {
		ChannelPipeline pipeline = channel.pipeline();
		
		pipeline.addLast(ChatConstants.FRAMER_KEY, new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
		pipeline.addLast(ChatConstants.DECODER_KEY, new StringDecoder());
		pipeline.addLast(ChatConstants.ENCODER_KEY, new StringEncoder());
		pipeline.addLast(ChatConstants.HANDLER_KEY, new ChatClientHandler());
	}

}
