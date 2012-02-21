package me.greatman.Craftconomy.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
}
