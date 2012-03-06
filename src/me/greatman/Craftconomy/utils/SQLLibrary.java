package me.greatman.Craftconomy.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import me.greatman.Craftconomy.ILogger;

import com.sun.rowset.CachedRowSetImpl;

@SuppressWarnings("restriction")
public class SQLLibrary
{

	private String databaseUrl, username, password;
	private DatabaseType type;
	private Connection connection;
	public SQLLibrary(String url, String databaseUsername, String databasePassword, DatabaseType dbtype)
	{
		setUrl(url);
		setUsername(databaseUsername);
		setPassword(databasePassword);
		type = dbtype;
	}
	public void setUrl(String url)
	{
		databaseUrl = url;
	}

	public void setUsername(String databaseUsername)
	{
		username = databaseUsername;
	}

	public void setPassword(String databasePassword)
	{
		password = databasePassword;
	}

	public Connection createConnection()
	{
		Connection conn = null;
		
		try
		{
			
			Class.forName("org.sqlite.JDBC");
			Class.forName("com.mysql.jdbc.Driver");
			if (connection != null)
			{
				if(connection.isClosed())
				{
					conn = DriverManager.getConnection(databaseUrl, username, password);
					connection = conn;
				}
				else
				{
					conn = connection;
				}
			}
			else
			{
				if (type == DatabaseType.SQLITE)
				{
					conn = DriverManager.getConnection(databaseUrl);
				}
				else 
				{
					conn = DriverManager.getConnection(databaseUrl, username, password);
					connection = conn;
				}
					
			}			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return conn;
	}

	public  CachedRowSetImpl query(String query, boolean result) throws SQLException
	{
		ResultSet rs = null;
		CachedRowSetImpl crs = null;
		Connection conn = createConnection();
		Statement sqlStatement;
		sqlStatement = conn.createStatement();
		if (result)
		{
			rs = sqlStatement.executeQuery(query);
			crs = new CachedRowSetImpl();
			crs.populate(rs);
		}

		else sqlStatement.executeUpdate(query);
		if(type == DatabaseType.SQLITE)
			conn.close();
		return crs;
		// return rs;
	}

	public  boolean checkTable(String table)
	{
		boolean result = false;
		ResultSet query;
		try
		{
			query = query("SELECT * FROM " + table, true);
			if (query != null)
				result = true;
		} catch (SQLException e)
		{
			ILogger.info("Table " + table + " does not exist!");
		}

		return result;
	}

	public void closeMySQL()
	{
		try
		{
			if (connection != null && !connection.isClosed())
			{
				connection.close();
			}
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void truncateTable(String string)
	{
		try
		{

			if (type == DatabaseType.MYSQL)
				query("TRUNCATE TABLE " + string, false);
			else if (type == DatabaseType.SQLITE)
				query("DELETE FROM " + string, false);

		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public DatabaseType getType()
	{
		return type;
	}

	public HashMap<Integer,HashMap<String,String>> selectEntry(String tableName, List<String> searchList, HashMap<String,String> search)
	{
		HashMap<Integer, HashMap<String, String>> map = new HashMap<Integer, HashMap<String, String>>();;
		String table = "SELECT ";
		Iterator<String> iterator = searchList.iterator();
		while(iterator.hasNext())
		{
			table += iterator.next();
			if (iterator.hasNext())
			{
				table += ",";
			}
		}
		table += " FROM " + tableName + " WHERE " + seperator(search, 1);
		
		try
		{
			ResultSet result = query(table, true);
			if (result != null)
			{
				int i = 0;
				while (result.next())
				{
					Iterator<String> theSearch = searchList.iterator();
					HashMap<String,String> secondMap = new HashMap<String,String>();
					while (theSearch.hasNext())
					{
						String name = theSearch.next();
						secondMap.put(name, result.getString(name));
					}
					map.put(i, secondMap);
					i++;
				}
			}
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	
	public boolean deleteEntry(String tableName, HashMap<String, String> entry)
	{
		boolean ok = false;
		String table = "DELETE FROM " + tableName + " WHERE " + seperator(entry, 1);
		try
		{
			ResultSet result = query(table, false);
			if (result != null)
			{
				if (result.next())
				{
					ok = true;
				}
			}
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ok;
	}
	public boolean createEntry(String tableName, HashMap<String, String> entry)
	{
		boolean ok = false;
		String table = "INSERT INTO " + tableName + "(" + seperator(entry,0) + ") VALUES(" + seperator(entry, 2) + ")";
		
		try
		{
			ResultSet result = query(table, false);
			if (result != null)
			{
				if (result.next())
				{
					ok = true;
				}
			}
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ok;
	}
	public boolean entryExist(String tableName, HashMap<String, String> entry)
	{
		boolean ok = false;
		String table = "SELECT " + seperator(entry, 0) + " FROM " + tableName + " WHERE " + seperator(entry, 1);
		try
		{
			ResultSet result = query(table, true);
			if (result != null)
			{
				if (result.next())
				{
					ok = true;
				}
			}
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ok;
	}
	
	public boolean createTable(String tableName, HashMap<String, FieldType> fields)
	{
		boolean result = false;
		String table = "CREATE TABLE " + tableName + " (";
		Iterator<Entry<String, FieldType>> theSet = fields.entrySet().iterator();
		while(theSet.hasNext())
		{
			Entry<String, FieldType> entry = theSet.next();
			
			if (entry.getValue() == FieldType.DOUBLE)
			{
				table += entry.getKey() + " DOUBLE NOT NULL";
			}
			else if (entry.getValue() == FieldType.DOUBLENULL)
			{
				table += entry.getKey() + " DOUBLE";
			}
			else if (entry.getValue() == FieldType.INT)
			{
				table += entry.getKey() + " INTEGER NOT NULL";
			}
			else if (entry.getValue() == FieldType.INTNULL)
			{
				table += entry.getKey() + " INTEGER";
			}
			else if (entry.getValue() == FieldType.VARCHAR)
			{
				table += entry.getKey() + " VARCHAR(40) NOT NULL";
			}
			else if (entry.getValue() == FieldType.VARCHARNULL)
			{
				table += entry.getKey() + " VARCHAR";
			}
			else if (entry.getValue() == FieldType.BOOLEAN)
			{
				table += entry.getKey() + " BOOLEAN";
			}
			else if (entry.getValue() == FieldType.PRIMARY)
			{
				table += entry.getKey() + " INTEGER NOT NULL";
				if (getType() == DatabaseType.MYSQL)
				{
					table += " AUTO_INCREMENT PRIMARY KEY";
				}
				else if (getType() == DatabaseType.SQLITE)
				{
					table += " PRIMARY KEY AUTOINCREMENT";
				}
			}
			if (theSet.hasNext())
			{
				table += ",";
			}
			
		}
		table += ")";
		try
		{
			query(table, false);
			result = true;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
	
	private static String seperator(HashMap<String, String> entry, int type)
	{
		String seperator = "";
		Iterator<Entry<String, String>> theSet = entry.entrySet().iterator();
		while(theSet.hasNext())
		{
			Entry<String, String> item = theSet.next();
			if (type == 0)
			{
				
				seperator += item.getKey();
				if (theSet.hasNext())
				{
					seperator += ",";
				}
			}
			else if (type == 1)
			{
				seperator += item.getKey() + "=";
				if (isIntParsable(item.getValue()))
				{
					seperator += Integer.parseInt(item.getValue() + "");
				}
				else if (isDoubleParsable(item.getValue()))
				{
					seperator += Double.parseDouble(item.getValue()) + "";
				}
				else
				{
					seperator += " '" + item.getValue() + "' ";
				}
				if (theSet.hasNext())
				{
					seperator += " AND ";
				}
			}
			else if (type == 2)
			{
				
				if (isIntParsable(item.getValue()))
				{
					seperator += Integer.parseInt(item.getValue() + "");
				}
				else if (isDoubleParsable(item.getValue()))
				{
					seperator += Double.parseDouble(item.getValue()) + "";
				}
				else
				{
					seperator += " '" + item.getValue() + "' ";
				}
				if (theSet.hasNext())
				{
					seperator += ",";
				}
			}
			
		}
		return seperator;
	}
	
	private static boolean isDoubleParsable(String text)
	{
		boolean ok = false;
		try{
			Double.parseDouble(text);
			ok = true;
		}
		catch (NumberFormatException e)
		{}
		return ok;
	}
	private static boolean isIntParsable(String text)
	{
		boolean ok = false;
		try{
			Integer.parseInt(text);
			ok = true;
		}
		catch (NumberFormatException e)
		{}
		return ok;
	}
}