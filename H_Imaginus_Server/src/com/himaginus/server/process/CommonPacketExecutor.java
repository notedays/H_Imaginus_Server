package com.himaginus.server.process;

import com.himaginus.common.packet.RequestCode;
import com.himaginus.common.packet.RequestPacket;
import com.himaginus.common.packet.ResponseCode;
import com.himaginus.common.util.TestUtil;

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
		switch (request.getCode()) {
		case REQUEST_TEST: {
			TestUtil.printMessage("TEST_CONTEXT : " + request.getContext());
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
