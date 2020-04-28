package com.generic.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.commons.lang.RandomStringUtils;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;

/**
 *  Contains generic utility function
 * @author Harshvardhan Yadav(Expleo)
 */
public class GenericUtils 
{
	/**
	 * return require date with increment/decrement in days from current date
	 * @param 	: incOrDecDays - Number by which user want increase/decrease date
	 * @param 	: sExpectedDateFormat - User expected date format eg. 9 april 2014
	 *        		--- dd/MM/yyyy -> 09/04/2015, dd-MM-yyyy -> 09-04-2015
	 * @param 	: timeZoneId - Time Zone
	 * @Author 	: Harshvardhan Yadav (Expleo)
	 */
	public static String getRequiredDay(String incOrDecDays, String expectedDateFormat, String timeZoneId) {
		try {
			DateFormat dateFormat;
			Calendar calendar = Calendar.getInstance();
			dateFormat = new SimpleDateFormat(expectedDateFormat);
			if (timeZoneId != null && !timeZoneId.equals(""))
				dateFormat.setTimeZone(TimeZone.getTimeZone(timeZoneId));
			calendar.add(Calendar.DAY_OF_MONTH, Integer.parseInt(incOrDecDays));
			Date tomorrow = calendar.getTime();
			String formattedDate = dateFormat.format(tomorrow);
			return formattedDate;
		} catch (Exception exception) {
			exception.printStackTrace();
			return "";
		}
	}

	/**
	 * return require date with increment/decrement in months from current date
	 * @param 	: incOrDecMonth Number by which user want increase/decrease month
	 * @param 	: sExpectedDateFormat - User expected date format eg. 9 april 2014
	 *        		--- dd/MM/yyyy -> 09/04/2015, dd-MM-yyyy -> 09-04-2015
	 * @param 	: timeZoneId - Time Zone
	 * @Author 	: Harshvardhan Yadav (Expleo)
	 */
	public static String getRequiredMonth(String incOrDecMonth, String expectedDateFormat, String timeZoneId) {
		try {
			DateFormat dateFormat;
			Calendar calendar = Calendar.getInstance();
			dateFormat = new SimpleDateFormat(expectedDateFormat);
			if (timeZoneId != null && !timeZoneId.equals(""))
				dateFormat.setTimeZone(TimeZone.getTimeZone(timeZoneId));
			calendar.add(Calendar.MONTH, Integer.parseInt(incOrDecMonth));
			Date tomorrow = calendar.getTime();
			String formattedDate = dateFormat.format(tomorrow);
			return formattedDate;
		} catch (Exception exception) {
			exception.printStackTrace();
			return "";
		}
	}

	/**
	 *	return require date with increment/decrement in years from current date
	 * @param 	: incOrDecYear Number by which user want increase/decrease date
	 * @param 	: sExpectedDateFormat - User expected date format eg. 9 april 2014
	 *        		--- dd/MM/yyyy -> 09/04/2015, dd-MM-yyyy -> 09-04-2015
	 * @param 	: timeZoneId - Time Zone
	 * @Author 	: Harshvardhan Yadav (Expleo)
	 */
	public static String getRequiredDateWithCustomYear(String incOrDecYear, String expectedDateFormat, String timeZoneId) {
		try {
			DateFormat dateFormat;
			Calendar calendar = Calendar.getInstance();
			dateFormat = new SimpleDateFormat(expectedDateFormat);
			if (timeZoneId != null && !timeZoneId.equals(""))
				dateFormat.setTimeZone(TimeZone.getTimeZone(timeZoneId));
			calendar.add(Calendar.YEAR, Integer.parseInt(incOrDecYear));
			Date tomorrow = calendar.getTime();
			String formattedDate = dateFormat.format(tomorrow);
			return formattedDate;
		} catch (Exception exception) {
			exception.printStackTrace();
			return "";
		}
	}

	/**
	 *  This method will converted date into excepted date format
	 *  @param	: originalDateFormat - original date format
	 *  @param 	: sExpectedDateFormat - User expected date format
	 *  @Author : Harshvardhan Yadav (Expleo)
	 */
	public static String getFormatedDate(String date, String originalDateFormat, String expectedDateFormat) {
		try {
			DateFormat inputFormatter = new SimpleDateFormat(originalDateFormat);
			Date originalDate = inputFormatter.parse(date);
			DateFormat outputFormatter = new SimpleDateFormat(expectedDateFormat);
			String expectedDate = outputFormatter.format(originalDate);
			return expectedDate;
		} catch (Exception exception) {
			exception.printStackTrace();
			return "";
		}
	}

	/**
	 * return require date with increment/decrement in years from user provided date
	 *  @param 	: incOrDecDays - Number by which user want increase/decrease date
	 *  @param 	: date - user provided date
	 *  @param 	: userDateFormat - user provided date format
	 * @Author : Harshvardhan Yadav (Expleo)
	 */
	public static String modifyDaysFromDate(String incOrDecDays, String date, String userDateFormat) {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(userDateFormat);
			Calendar c = Calendar.getInstance();
			c.setTime(dateFormat.parse(date));
			c.add(Calendar.DATE, Integer.parseInt(incOrDecDays));
			String convertedDate = dateFormat.format(c.getTime());
			return convertedDate;
		} catch (ParseException e) {
			e.printStackTrace();
			return "";
		}

	}

	/**
	 *  copy files from source loaction to destination location
	 * @param : Soure file path
	 * @param : destination file path
	 * @Author : Harshvardhan Yadav (Expleo)
	 */
	public static void copyFileUsingStream(String sourceFilePath, String destinationFilePath) {
		InputStream inputStream = null;
		OutputStream outputStream = null;
		try {
			inputStream = new FileInputStream(new File(sourceFilePath));
			outputStream = new FileOutputStream(new File(destinationFilePath));
			byte[] buffer = new byte[1024];
			int length;
			while ((length = inputStream.read(buffer)) > 0) {
				outputStream.write(buffer, 0, length);
			}
		} catch (Exception exception) {
			exception.printStackTrace();

		} finally {
			try {
				inputStream.close();
				outputStream.close();
			} catch (IOException iOException) {
				iOException.printStackTrace();
			}
		}
	}

	/**
	 * convert month to number
	 * @Author : Harshvardhan Yadav (Expleo)
	 */
	public static String getMonthInNumber(String expectedMonth){
		try{
			Date date;
			date = new SimpleDateFormat("MMM", Locale.ENGLISH).parse(expectedMonth);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			int month = cal.get(Calendar.MONTH);
			month = month + 1;
			String strMonth = String.valueOf(month);
			if(strMonth.length() == 1)
				strMonth = "0" + strMonth;
			return strMonth;
		}catch(Exception exp){
			exp.printStackTrace();
			return "";
		}
	} 

	public static String getRandomAlphabeticString(int count) {
		return RandomStringUtils.randomAlphabetic(count);
	}

	public static String getRandomAlphanumericString(int count) {
		return RandomStringUtils.randomAlphanumeric(count);
	}

	public static String getRandomNumeric(int count) {
		return RandomStringUtils.randomNumeric(count);
	}

	public static String getRandomAlphanumericEmailString(int count, String emailAddress) {
		return "e" + RandomStringUtils.randomAlphanumeric(count) + emailAddress;
	}

	public static void writeAppiumLogs(LogEntries appiumLogEntries){
		FileWriter writer = null; 
		try
		{
			writer = new FileWriter(new File(System.getProperty("user.dir") + "/src/test/resources/Logs/AppiumLog.txt"), true);
			StringBuilder buf = new StringBuilder();
			buf.append("Appium Driver LOGS:\n\n");
			for (final LogEntry entry : appiumLogEntries) {
				buf.append(new Date(entry.getTimestamp()) + " " + entry.getLevel() + " " + entry.getMessage() + "\n");
				writer.write(buf.toString());
			}
		}
		catch ( Exception exception){
			exception.printStackTrace();
		}
		finally {
			try {
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}