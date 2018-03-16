package com.himaginus.server.process;

import com.himaginus.common.packet.RequestPacket;
import com.himaginus.server.process.PacketExecutor;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;

public class PacketHandler extends ChannelInboundHandlerAdapter {
	PacketExecutor executor;
	MainProcess mainProcess = MainProcess.getInstance();

	public PacketHandler(PacketExecutor executor) {
		this.executor = executor;
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		try {
			RequestPacket mrp = (RequestPacket) msg;
			if (mainProcess.findUser(ctx) == null) {
				CommonPacketExecutor.getInstance().execute(ctx, mrp);
			} else {
				executor.execute(ctx, mrp);
			}
		} finally {
			// object reference를 풀어주기 위해서 반드시 release 해야한다.
			ReferenceCountUtil.release(msg);
		}
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if (evt instanceof IdleStateEvent) {
			IdleStateEvent event = (IdleStateEvent) evt;
			if (event.state() == IdleState.READER_IDLE) {
				ctx.close();
				channelInactive(ctx);
			}
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.fireExceptionCaught(cause);
		cause.printStackTrace();
		channelInactive(ctx);
		ctx.close();
	}
}
