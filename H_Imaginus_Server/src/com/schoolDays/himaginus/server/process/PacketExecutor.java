package com.schoolDays.himaginus.server.process;

import com.himaginus.common.packet.RequestPacket;

import io.netty.channel.ChannelHandlerContext;

public interface PacketExecutor {
	void execute(ChannelHandlerContext session, RequestPacket request);
}
