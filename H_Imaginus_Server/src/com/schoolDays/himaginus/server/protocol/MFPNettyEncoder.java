package com.schoolDays.himaginus.server.protocol;

import com.himaginus.common.packet.ResponsePacket;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class MFPNettyEncoder extends MessageToByteEncoder<ResponsePacket>{

	@Override
	protected void encode(ChannelHandlerContext ctx, ResponsePacket msg, ByteBuf out) throws Exception {
		System.out.println("[INFO] PACKET RESPONSE!\nCODE : "+msg.getResponseCode()+"\nTO : "+ctx.channel().remoteAddress());
		out.writeBytes(msg.toByteArray());
	}
}
