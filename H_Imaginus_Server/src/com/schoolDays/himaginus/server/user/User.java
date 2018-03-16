package com.schoolDays.himaginus.server.user;

import com.himaginus.common.packet.ResponsePacket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

public class User {

	private final ChannelHandlerContext session;

	// # 생성자 호출 시 반드시 channel 넣어줄 것!
	public User(ChannelHandlerContext session) {
		this.session = session;
	}

	long id;
	String nickname;

	// # Useful Methods =====================================================

	/**
	 * 유저에게 Response 보내른 메소드
	 * @param packet
	 */
	public void sendPacket(ResponsePacket packet) {
		session.writeAndFlush(packet);
	}

	// # Getter / Setter ====================================================
	public ChannelHandlerContext getSession() {
		return session;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

}
