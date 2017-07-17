package com.schoolDays.himaginus.server.process;

import com.himaginus.common.data.StringData;
import com.himaginus.common.packet.RequestPacket;
import com.himaginus.common.packet.ResponsePacket;

import io.netty.channel.ChannelHandlerContext;

public class PacketExecutor {

	MainProcess process;

	public PacketExecutor(MainProcess process) {
		this.process = process;
	}
	
	public void execute(ChannelHandlerContext session, RequestPacket mrp) {
		switch (mrp.getRequestCode()) {
		case 1:
			ResponsePacket response = new ResponsePacket();
			response.setResponseCode(1);
			response.addResponseData(new StringData());
			session.channel().write(mrp.getContext());
			break;
		}
	}
}
