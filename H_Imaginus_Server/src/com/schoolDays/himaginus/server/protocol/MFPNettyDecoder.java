package com.schoolDays.himaginus.server.protocol;

import java.nio.charset.Charset;
import java.util.List;

import com.himaginus.common.packet.RequestPacket;
import com.himaginus.common.util.CommonUtil;
import com.schoolDays.himaginus.server.main.ServerMain;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class MFPNettyDecoder extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		// isEnough = iobuffer.prefixedDataAvailable(4, 1024);
		if(in.readableBytes()<4){
			return;
		}
		in.markReaderIndex();
		RequestPacket mrp = new RequestPacket();
		int tmpByteSize = in.readInt() - CommonUtil.getLengthDataSize();
		if(tmpByteSize>ServerMain.MAX_RECEIVE_SIZE){
			in.resetReaderIndex();
			throw new RuntimeException();
		}
		if(in.readableBytes()<tmpByteSize+4){
			in.resetReaderIndex();
			return;
		}
		byte[] tmpByte = new byte[tmpByteSize];
		mrp.setRequestCode(in.readInt());
		in.readBytes(tmpByte);
		mrp.setContext(new String(tmpByte, Charset.forName("UTF-8")));
		out.add(mrp);
		ctx.flush();
	}

}
