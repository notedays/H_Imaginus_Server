package com.schoolDays.himaginus.server.database;

import java.util.HashMap;

public class DataMap<K,V> extends HashMap<K,V>{
	
	private static final long serialVersionUID = 1L;

	public int getInt(K key){
		V value = get(key);
		if(value == null) return 0;
		else return (int)value;
	}
	
	public long getLong(K key){
		V value = get(key);
		if(value == null) return 0l;
		else return (long)value;
	}
	
	public float getFloat(K key){
		V value = get(key);
		if(value == null) return 0f;
		else return (float)value;
	}
	
	public double getDouble(K key){
		V value = get(key);
		if(value == null) return 0d;
		else return (double)value;
	}
	
	public boolean getBoolean(K key){
		V value = get(key);
		if(value == null) return false;
		else return (boolean)value;
	}
	
	public String getString(K key){
		V value = get(key);
		if(value == null) return null;
		else return (String)value;
	}
	
}
