package com.himaginus.server.main;

import java.io.File;
import java.io.IOException;

import com.himaginus.server.configuration.ServerConfig;
import com.himaginus.server.process.PacketExecutor;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.timeout.IdleStateHandler;

public class ServerMain {
	public static final int MAX_RECEIVE_SIZE = 2048;
	
	/**
	 * 네티 서버를 load하는 함수
	 * @param config : 서버 관련 값(DB 정보 및 기본적인 서버 정보)들을 가진 config.properties 파일
	 * @param handler : 채널을 다룰 핸들러
	 * @param executor : 패킷 정보를 다룰 Executor
	 */
	public static void load(File config, ChannelHandler handler) throws InterruptedException {
		ServerConfig.load(config);
		
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						public void initChannel(SocketChannel ch) throws Exception {
							ch.pipeline().addLast("idlehandler", new IdleStateHandler(60 * 5, 0, 0));
							ch.pipeline().addLast("ObjectEncoder", new ObjectEncoder());
							ch.pipeline().addLast("ObjectDecocder", new ObjectDecoder(1024 * 1024,
									ClassResolvers.weakCachingConcurrentResolver(this.getClass().getClassLoader())));
							ch.pipeline().addLast("PacketHandler", handler);
						}
					}).childOption(ChannelOption.SO_KEEPALIVE, true)
					.childOption(ChannelOption.SO_RCVBUF, MAX_RECEIVE_SIZE);

			// Bind and start to accept incoming connections.
			ChannelFuture f = b.bind(ServerConfig.port).sync();

			// Wait until the server socket is closed.
			// In this example, this does not happen, but you can do that to
			// gracefully
			// shut down your server.
			f.channel().closeFuture().sync();
		} finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}
}
