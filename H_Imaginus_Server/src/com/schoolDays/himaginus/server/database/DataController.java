package com.schoolDays.himaginus.server.database;

public class DataController {
	
	private static DataController dc = new DataController();
	private DataController() {}
	
	public static DataController getInstance(){
		return dc;
	}
	
}
