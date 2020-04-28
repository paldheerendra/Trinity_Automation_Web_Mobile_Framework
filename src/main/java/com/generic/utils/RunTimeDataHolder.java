package com.generic.utils;

import java.util.Hashtable;
/**
 * This class holds runtime data of test script which can be used in 
 * different framework layers user can store/retrive from pojo getter/setter of class
 * @author Harshvardhan Yadav(Expleo)
 *
 */
public class RunTimeDataHolder {
	
	private Hashtable<String, Object> runTimeDataHolder =  new Hashtable<String, Object>();
	
	public void storeData(String key, Object runtimeData) {
		this.runTimeDataHolder.put(key, runtimeData);
	}
	
	public Object retriveData(String key) {
		return this.runTimeDataHolder.get(key);
	}
}