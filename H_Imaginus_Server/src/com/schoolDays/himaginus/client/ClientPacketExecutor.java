package com.schoolDays.himaginus.client;

import com.himaginus.common.data.BasicData;
import com.himaginus.common.packet.ResponsePacket;

import io.netty.channel.ChannelHandlerContext;

public class ClientPacketExecutor {
	
	// # SingleTone
	private static ClientPacketExecutor executor = new ClientPacketExecutor();
	private ClientPacketExecutor() {}
	public static ClientPacketExecutor getInstance(){
		return executor;
	}
	
	// # Execute Response from Server
	public void execute(ChannelHandlerContext ctx, ResponsePacket response){
		switch (response.getCode()) {
		case ResponsePacket.TEST:
			BasicData<Integer> data = response.getData(0);
			System.out.println("서버로 부터 받은 메세지 : "+(data.getMsg() * 2));
			break;
		}
	}
}
