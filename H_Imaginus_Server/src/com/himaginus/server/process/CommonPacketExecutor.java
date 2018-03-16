package com.himaginus.server.process;

import com.himaginus.common.packet.RequestCode;
import com.himaginus.common.packet.RequestPacket;
import com.himaginus.common.packet.ResponseCode;
import com.himaginus.common.packet.ResponsePacket;
import com.himaginus.common.util.TestUtil;
import com.himaginus.server.configuration.ServerConfig;

import io.netty.channel.ChannelHandlerContext;

public class CommonPacketExecutor implements PacketExecutor, RequestCode, ResponseCode {
	private static CommonPacketExecutor executor = new CommonPacketExecutor();

	private CommonPacketExecutor() {
	}

	public static CommonPacketExecutor getInstance() {
		return executor;
	}

	@Override
	public void execute(ChannelHandlerContext session, RequestPacket request) {
		if(ServerConfig.isTestMode) {
			TestUtil.printRequest(request, session.channel().remoteAddress().toString());
		}
		switch (request.getCode()) {
		case REQUEST_TEST: {
			session.writeAndFlush(new ResponsePacket(RESPONSE_TEST));
			break;
		}
		case REQUEST_REGIST: {

			break;
		}
		default:
			break;
		}
	}

}
