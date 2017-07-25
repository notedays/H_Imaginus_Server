package com.schoolDays.himaginus.client;

import com.himaginus.common.packet.ResponsePacket;

import data.CityData;
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
		case ResponsePacket.TEST:{
			if(response.isSuccess()){
				CityData data = response.<CityData>getData(0);
				System.out.println("==="+data.getName()+"'s Info===");
				System.out.println("ID : "+data.getId());
				System.out.println("CountryCode : "+data.getCountryCode());
				System.out.println("District : "+data.getDistrict());
				System.out.println("Population : "+data.getPopulation());
			}
			break;
		}
		case ResponsePacket.REGIST:{
			if(response.isSuccess()){
				System.out.println("가입에 성공 했습니다.");
			}else{
				System.out.println("가입에 실패 했습니다.");
			}
			break;
		}
		}
	}
}
