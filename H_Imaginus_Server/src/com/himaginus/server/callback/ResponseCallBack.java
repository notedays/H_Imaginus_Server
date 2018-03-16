package com.himaginus.server.callback;

import com.himaginus.common.callback.CallBack;
import com.himaginus.common.packet.ResponsePacket;
import com.himaginus.common.util.Variance;
import com.himaginus.server.user.User;

public class ResponseCallBack implements CallBack {
	
	User user;
	ResponsePacket response;
	
	@Override
	public void call() {
		new Variance<User>(user).ifNotNull(() -> user.sendPacket(response));
	}
	
	// # Getter / Setter
	public User getUser() {
		return user;
	}

	public ResponseCallBack setUser(User user) {
		this.user = user;
		return this;
	}

	public ResponsePacket getResponse() {
		return response;
	}

	public ResponseCallBack setResponse(ResponsePacket response) {
		this.response = response;
		return this;
	}
}
