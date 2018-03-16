package com.himaginus.server.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import com.himaginus.common.packet.RequestPacket;
import com.himaginus.common.packet.ResponsePacket;
import com.himaginus.common.util.TestUtil;

public class ServerConfig {

	public static int port;

	public static int channelId;

	public static String dbDriver;
	public static String dbHost;
	public static String dbUser;
	public static String dbPassword;

	public static boolean isTestMode = true;

	public static void load(File file, RequestPacket loadRequest, ResponsePacket loadResponse) {
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(file));
		} catch (Exception e) {
			e.printStackTrace();
		}
		port = Integer.parseInt(prop.getProperty("port", "8808"));
		channelId = Integer.parseInt(prop.getProperty("channelId", "0"));
		dbDriver = prop.getProperty("dbDriver", "com.mysql.jdbc.Driver");
		dbHost = prop.getProperty("dbHost");
		dbUser = prop.getProperty("dbUser");
		dbPassword = prop.getProperty("dbPassword");

		if (isTestMode) {
			TestUtil.loadRequestMap(loadRequest);
			TestUtil.loadResponseMap(loadResponse);
		}
	}
}
