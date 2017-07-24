package com.schoolDays.himaginus.server.process;

import com.himaginus.common.data.BasicData;
import com.himaginus.common.packet.RequestPacket;
import com.himaginus.common.packet.ResponsePacket;
import com.schoolDays.himaginus.server.database.DataController;

import io.netty.channel.ChannelHandlerContext;

public class PacketExecutor {

	MainProcess process;
	DataController dc = DataController.getInstance();
	
	public PacketExecutor(MainProcess process) {
		this.process = process;
	}
	
	public void execute(ChannelHandlerContext session, RequestPacket request) {
		ResponsePacket response;
		switch (request.getCode()) {
		case 1:
			response = new ResponsePacket();
			response.setCode(ResponsePacket.TEST);
			response.addResponseData(new BasicData<Integer>(Integer.parseInt(request.getContext())));
			session.writeAndFlush(response);
			break;
		}
	}
	
	public void send(ChannelHandlerContext ctx, ResponsePacket response){
		ctx.writeAndFlush(response);
	}
}
