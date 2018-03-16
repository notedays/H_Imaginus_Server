package com.himaginus.server.process;

import java.util.Hashtable;

import com.himaginus.server.user.User;

import io.netty.channel.ChannelHandlerContext;

public class MainProcess {
	// # Single Tone 처리 ============================================================================
	private MainProcess() {
	}

	private static final MainProcess MAIN_PROCESS = new MainProcess();

	public static MainProcess getInstance() {
		return MAIN_PROCESS;
	}

	// # 메인 프로세스 관련 변수 정의 ========================================================================
	public Hashtable<ChannelHandlerContext, User> userTable = new Hashtable<>(); // @ 서버에 로그인 성공한 유저 채널을 담는 테이블
	
	// # 기능 관련 메소드 구현 =============================================================================
	public void loginUser(User user) {
		if(user != null) {
			userTable.put(user.getSession(), user);
		}
	}
	
	public User findUser(ChannelHandlerContext session) {
		return userTable.get(session);
	}
}
