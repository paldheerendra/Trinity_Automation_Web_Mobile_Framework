package com.generic.utils;
 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
 

/**
 * This class instantiates database connection to any database
 * also it contains create connection to database, execute of
 * DDL and DML query 
 * @author Harshvardhan Yadav(Expleo)
 */
public class DBManager
{

 	public static Connection getDatabaseConnection(String driverName,String url,String userName,String password)
	{
		registerDriver(driverName);
		Connection conn = connectToDB(url,userName,password);
		return conn;
	}
	
	public static Connection getDatabaseConnection(String driverName,String connectionString)
	{
		registerDriver(driverName);
		Connection conn=connectToDB(connectionString);
		return conn;
	}
	
	public static void closeConnection(Connection conn)
	{
		try 
		{
			if(!conn.isClosed())
				conn.close();	
		} 
		catch (SQLException sQLException) 
		{
			sQLException.printStackTrace();
		}
	}
	
	public static ResultSet executeSelectQuery(Connection conn, String selectQuery)
	{
		PreparedStatement pstmt = null;
		ResultSet rset = null;	
		try
		{
			pstmt = conn.prepareStatement(selectQuery);			
			rset=pstmt.executeQuery();
		}
		catch (SQLException sQLException) 
		{
			sQLException.printStackTrace();
		}
		return rset;
	}
	
	 
	
	public static int getCount(ResultSet result)
	{
		int count=0;
		try{
			while(result.next())
			{
				count++;
			}
		}catch(SQLException sQLException){
			sQLException.printStackTrace();
		}
		return count;
	}
	
	public static int executeDDLQuery(Connection conn, String ddlQuery)
	{
		PreparedStatement pstmt = null;
		int resut=-1;
		try
		{
			pstmt = conn.prepareStatement(ddlQuery);
			resut=pstmt.executeUpdate();
		}
		catch (SQLException sQLException) 
		{
			sQLException.printStackTrace();
		}
		return resut;
	}
	
	public static int executeDDLQuery(String dbDriver, String connectionString, String ddlQuery)
	{
		int data=0;
		Connection con=getDatabaseConnection(dbDriver, connectionString);
		try
		{
			data=DBManager.executeDDLQuery(con, ddlQuery);
		}
		catch(Exception exception)
		{ 
			System.out.println("Problem while executing the query");
			System.out.println(String.format("DBDriver[%s] ConnString[%s] Query[%s]", dbDriver, connectionString, ddlQuery));
			exception.printStackTrace();
		}
		finally
		{
			DBManager.closeConnection(con);
		}	
		return data;
	}
	 
	private static void registerDriver(String driverName)
	{
		try 
		{
			Class.forName(driverName);
		}
		catch (ClassNotFoundException exception) 
		{
			System.out.println("Unable to load Driver(%s). Please check the Database driver name...!!!" +  driverName);
			exception.printStackTrace();
		} 
	}
	
	private static Connection connectToDB(String url,String user,String password)
	{
		Connection conn=null;
		try 
		{
			conn=DriverManager.getConnection(url, user, password);
		} 
		catch (SQLException exception) 
		{
			System.out.println("Problem occured while connecting to DB with Url=%s , UserName=%s , Password=%s " + url + user + password);
			exception.printStackTrace();
		}
		return conn;
	}
	
	private static Connection connectToDB(String connectionString)
	{
		Connection conn=null;
		try 
		{
			conn=DriverManager.getConnection(connectionString);			
		} 
		catch (SQLException sQLException) 
		{
			System.out.println("Problem occured while connecting to DB with connectinString=%s" + connectionString);
			sQLException.printStackTrace();
		}
		return conn;
	}
	
	/**
	 * Execute Select Query and get result as the Collection Object.
	 * This function will return the resltSet as the List<HashMap<String.String>>
	 * Each HashMap Object in the List is one row with key(as columnName) and value(as column value).	
	 * @param driverName
	 * @param connectionString
	 * @param sql - select Query
	 * @return List<HashMap<String, String>>
	 * if it there is no data it will return empty List
	 */
	public static List<HashMap<String, String>> executeSelectQuery(String driverName,String connectionString,String sql) 
	{
		Connection con = DBManager.getDatabaseConnection(driverName, connectionString);
		List<HashMap<String, String>> records = null;
		try
		{
			ResultSet resultSet = DBManager.executeSelectQuery(con, sql);
			records = getResultSetAsListOfHashMap(resultSet);
		}
		catch(Exception ex)
		{
			System.out.println("Problem while executing the query");
			System.out.println(String.format("DBDriver[%s] ConnString[%s] Query[%s]", driverName, connectionString, sql));
	 	}
		finally
		{
			DBManager.closeConnection(con);
		}
		return records;
	}
	
	/**
	 * This Function Connect to the data base as per the connection string.
	 * It consider the database Driver as the DB2 driver. This function returns only the single String value from
	 * database as per query executed.
	 * @param connectionString
	 * @param sql query
	 * @param timeInSeconds
	 * @return returns value of first column of result set 
	 */
	public static String getValueFromDataBase(String dbDriver, String connectionString,String sql) 
	{
		Connection con= getDatabaseConnection(dbDriver, connectionString);
		ResultSet res= executeSelectQuery(con, sql);
		String data = null;
		try 
		{
			if(res.next())
			{
				data = res.getString(1);
				return data;
			}
		} 
		catch (SQLException sQLException) 
		{
			System.out.println("Problem while getting data from DB. DriverName[%s],ConnectionString[%s],query[%s]" + dbDriver + connectionString + sql);
			sQLException.printStackTrace();
		}
		finally
		{
			closeConnection(con);
		}
		return data;
	}
	
	
	/**
	 * Convert your ResultSet object into Collection Object.
	 * This function will return the resltSet as the List<HashMap<String.String>>
	 * Each HashMap Object in the List is one Complete row with key(as columnName) and value(as column value). 
	 * @param resultSet- java.sql.ResultSet object
	 */
	public static List<HashMap<String, String>> getResultSetAsListOfHashMap(ResultSet resultSet)
	{
		List<HashMap<String, String>> records= new ArrayList<HashMap<String,String>>();
		try
		{
			while(resultSet.next())
			{
					HashMap<String,String> record= getResultSetAsHashMap(resultSet);
					records.add(record);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return records;
	}
	

	private static HashMap<String, String> getResultSetAsHashMap(ResultSet resultSet)
	{
		HashMap<String, String> record=new HashMap<String, String>();
		try
		{
			ResultSetMetaData metaData=resultSet.getMetaData();
			for (int i = 1; i <= metaData.getColumnCount(); i++) 
			{
				record.put(metaData.getColumnName(i),resultSet.getString(i));
			}
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
		}
		return record;
	}
}