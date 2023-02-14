package com.generic.utils;
 
import java.io.BufferedReader;
import java.io.InputStreamReader;
/**
 * Contains method to run commands on command prompt windows
 * @author Dheerendra Singh
 */
public class CommandPrompt 
{
	// Local Variables
	private static Process process;
	private static ProcessBuilder builder;
 
	public static String runCommand(String command)  
	{
		String allLine = "";
		try
		{
			String os = System.getProperty("os.name");
			//System.out.println(os);

			// build cmd proccess according to os
			if(os.contains("Windows")) // if windows
			{
				builder = new ProcessBuilder("cmd.exe","/c", command);
				builder.redirectErrorStream(true);
				Thread.sleep(1000);
				process = builder.start();
			}
		 
			// get std output
			BufferedReader buffReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = "";
			while((line = buffReader.readLine()) != null)
			{
				allLine = allLine + "" + line + "\n";
				if(line.contains("Console LogLevel: debug"))
					break;
			}
		}catch(Exception exception){
			exception.printStackTrace();
		}
		return allLine;
	}
}