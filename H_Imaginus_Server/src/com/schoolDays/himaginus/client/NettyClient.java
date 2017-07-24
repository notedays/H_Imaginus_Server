package com.schoolDays.himaginus.client;

import java.util.Scanner;

import com.himaginus.common.packet.RequestPacket;
import com.himaginus.common.packet.ResponsePacket;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

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
					ch.pipeline().addLast("ObjectEncoder", new ObjectEncoder());
					ch.pipeline().addLast("ObjectDecoder", new ObjectDecoder(1024 * 1024, 
							ClassResolvers.weakCachingConcurrentResolver(this.getClass().getClassLoader())));
					ch.pipeline().addLast("mfpNettyHandler", new NettyClientHandler());
				}
			});
			
			ChannelFuture f = b.connect(HOST, PORT);
			
			// ## TestCode
			Scanner sc = new Scanner(System.in);
			for(int i=0; i<3; i++){
				System.out.print("서버로 보낼 코드 : ");
				int code = sc.nextInt();
				sc.nextLine();
				System.out.print("서버로 보낼 메세지 : ");
				String context = sc.next();
				sc.nextLine();
				
				RequestPacket request = new RequestPacket(code, context);
				f.channel().writeAndFlush(request);
			}
			// ## End
			
			f.channel().closeFuture().sync();
		}finally{
			group.shutdownGracefully();
		}
	}
}

