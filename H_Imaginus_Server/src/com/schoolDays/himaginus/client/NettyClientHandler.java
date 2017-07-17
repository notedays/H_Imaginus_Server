package com.schoolDays.himaginus.client;

import java.nio.ByteBuffer;

import com.himaginus.common.data.StringData;
import com.himaginus.common.packet.RequestPacket;
import com.himaginus.common.packet.ResponsePacket;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class NettyClientHandler extends ChannelInboundHandlerAdapter{
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		RequestPacket request = new RequestPacket(1, "Hello");
		ByteBuf messageBuffer = Unpooled.buffer();
		messageBuffer.writeBytes(request.toByteArray());
		ctx.writeAndFlush(messageBuffer);
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuffer buffer = ((ByteBuf)msg).nioBuffer();
		byte[] bytes = new byte[buffer.remaining()];
		buffer.get(bytes);
		ResponsePacket response = ResponsePacket.fromByteArray(bytes);
		System.out.println( ((StringData)response.getResponseDataList().get(0)).getText() );
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		super.exceptionCaught(ctx, cause);
	}
	
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		super.channelInactive(ctx);
	}
}
