package com.schoolDays.himaginus.server.main;

import java.io.IOException;

import com.schoolDays.himaginus.server.configuration.ServerConfig;
import com.schoolDays.himaginus.server.process.MainProcess;
import com.schoolDays.himaginus.server.protocol.MFPNettyDecoder;
import com.schoolDays.himaginus.server.protocol.MFPNettyEncoder;
import com.schoolDays.himaginus.server.protocol.MFPNettyHandler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

public class ServerMain {
	public static final int MAX_RECEIVE_SIZE = 2048;

	public static void main(String[] args) throws IOException,InterruptedException {
		ServerConfig.load();
		MainProcess process = new MainProcess();
		
		 EventLoopGroup bossGroup = new NioEventLoopGroup(); 
	        EventLoopGroup workerGroup = new NioEventLoopGroup();
	        try {
	            ServerBootstrap b = new ServerBootstrap(); 
	            b.group(bossGroup, workerGroup)
	             .channel(NioServerSocketChannel.class) 
	             .childHandler(new ChannelInitializer<SocketChannel>() { 
	                 @Override
	                 public void initChannel(SocketChannel ch) throws Exception {
	                	 ch.pipeline().addLast("idlehandler", new IdleStateHandler(60*5, 0 ,0));
	                	 ch.pipeline().addLast("mfpNettyEncoder", new MFPNettyEncoder());
	                	 ch.pipeline().addLast("mfpNettyDecocder", new MFPNettyDecoder());
	                	 ch.pipeline().addLast("mfpNettyHandler", new MFPNettyHandler(process));
	                 }
	             })
	             .childOption(ChannelOption.SO_KEEPALIVE, true)
	             .childOption(ChannelOption.SO_RCVBUF, MAX_RECEIVE_SIZE);

	            // Bind and start to accept incoming connections.
	            ChannelFuture f = b.bind(ServerConfig.port).sync(); 

	            // Wait until the server socket is closed.
	            // In this example, this does not happen, but you can do that to gracefully
	            // shut down your server.
	            f.channel().closeFuture().sync();
	        } finally {
	            workerGroup.shutdownGracefully();
	            bossGroup.shutdownGracefully();
	        }
	}
}
