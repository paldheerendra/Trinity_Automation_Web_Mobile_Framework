package com.generic;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Class will load all test data, load all objects 
 * @author Dheerendra Singh
 */
public class BaseView 
{
	private Pojo pojo;
	public BaseView(Pojo objPojo) {
		pojo = objPojo;
	}
	
	/** 
	 * return runtime object of page or view
	 * */
	public <T> T PageObjectManager(Class<T> cls) {
		try {
			Constructor<T> constructor = cls.getConstructor(new Class[] { Pojo.class });
			return (T) constructor.newInstance(new Object[] { pojo });
		} catch (  InstantiationException | IllegalAccessException | InvocationTargetException | IllegalArgumentException
				| SecurityException | NoSuchMethodException e) {
			e.printStackTrace();
		}
		return null;
	}
}