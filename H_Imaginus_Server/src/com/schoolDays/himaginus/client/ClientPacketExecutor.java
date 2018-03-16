package com.schoolDays.himaginus.client;

import java.util.Iterator;
import java.util.Map.Entry;

import com.himaginus.common.data.CityData;
import com.himaginus.common.data.TestData;
import com.himaginus.common.packet.RequestCode;
import com.himaginus.common.packet.ResponseCode;
import com.himaginus.common.packet.ResponsePacket;

import io.netty.channel.ChannelHandlerContext;

public class ClientPacketExecutor implements RequestCode, ResponseCode{
	
	// # SingleTone
	private static ClientPacketExecutor executor = new ClientPacketExecutor();
	private ClientPacketExecutor() {}
	public static ClientPacketExecutor getInstance(){
		return executor;
	}
	
	// # Execute Response from Server
	public void execute(ChannelHandlerContext ctx, ResponsePacket response){
		switch (response.getCode()) {
		case RESPONSE_TEST:{
			if(response.isSuccess()){
				TestData test = response.<TestData>getData(0);
				for(CityData city : test.cityList){
					System.out.println("==="+city.getName()+"'s Info===");
					System.out.println("ID : "+city.getId());
					System.out.println("CountryCode : "+city.getCountryCode());
					System.out.println("District : "+city.getDistrict());
					System.out.println("Population : "+city.getPopulation());
				}
				
				Iterator<Entry<Integer,CityData>> iter = test.cityMap.entrySet().iterator();
				while(iter.hasNext()){
					Entry<Integer,CityData> entry = iter.next();
					System.out.println(entry.getKey() +" : "+entry.getValue().getName());
				}
				}
			break;
		}
		case RESPONSE_REGIST:{
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
