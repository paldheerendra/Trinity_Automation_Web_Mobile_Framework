package com.generic.logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Contains Log4j Logger for test environment  
 * @Author        : Dheerendra Singh
 */
public class Log4JLogger
{
	private Logger logger;

	/**
	 * Initizalize detailLogger 
	 */
	public void initializeLogger(){
		logger = LogManager.getLogger(Log4JLogger.class);
	}

	/**
	 * @return Log4JLogger private logger instance
	 */
	public Logger getLogger(){
		return logger;
	}
}
