package com.schoolDays.himaginus.server.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class ServerConfig {
	
	public static int port;

	public static int channelId;
	public static int channelType;
	
	public static String dbDriver;
	public static String dbHost;
	public static String dbUser;
	public static String dbPassword;
	
	public static boolean isTestMode;
	
	public static void load(){
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(new File("config.properties")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		port = Integer.parseInt(prop.getProperty("port","8808"));
		channelId = Integer.parseInt(prop.getProperty("channelId","0"));
		channelType = Integer.parseInt(prop.getProperty("channelType","1"));
		dbDriver = prop.getProperty("dbDriver","com.mysql.jdbc.Driver");
		dbHost = prop.getProperty("dbHost");
		dbUser = prop.getProperty("dbUser");
		dbPassword = prop.getProperty("dbPassword");
	}
}
