package com.schoolDays.himaginus.client;

import com.schoolDays.himaginus.server.protocol.MFPNettyDecoder;
import com.schoolDays.himaginus.server.protocol.MFPNettyEncoder;
import com.schoolDays.himaginus.server.protocol.MFPNettyHandler;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClient {
	public static void main(String[] args) throws Exception{
		new NettyClient();
	}
	
	public static final String HOST = "localhost";
	public static final int PORT = 8808;
	
	public NettyClient() throws Exception{
		EventLoopGroup group = new NioEventLoopGroup();
		
		try{
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioSocketChannel.class)
			.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
//					ch.pipeline().addLast("mfpNettyEncoder", new MFPNettyEncoder());
//					ch.pipeline().addLast("mfpNettyDecocder", new MFPNettyDecoder());
					ch.pipeline().addLast("mfpNettyHandler", new NettyClientHandler());
				}
			});
			
			ChannelFuture f = b.connect(HOST, PORT);
			
			f.channel().closeFuture().sync();
		}finally{
			group.shutdownGracefully();
		}
	}
}

