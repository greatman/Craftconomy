package me.greatman.iConomy7.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import com.sun.rowset.CachedRowSetImpl;

import me.greatman.iConomy7.iConomy;

@SuppressWarnings("restriction")
public class SQLLibrary {
	
	
	private static String 	databaseUrl,
							username,
							password;
	public static void setUrl(String url)
	{
		databaseUrl = url;
	}
	public static void setUsername(String databaseUsername)
	{
		username = databaseUsername;
	}
	public static void setPassword(String databasePassword)
	{
		password = databasePassword;
	}
	public static Connection createConnection() {
		  Connection conn = null;
		  try {
		    Class.forName("org.sqlite.JDBC");
		    Class.forName("com.mysql.jdbc.Driver");
		    if (DatabaseHandler.type == DatabaseHandler.databaseType.SQLITE)
		    	conn = DriverManager.getConnection(databaseUrl);
		    else
		    	conn = DriverManager.getConnection(databaseUrl, username, password);
		    
		    //conn = DriverManager.getConnection("jdbc:sqlite:" + iConomy.plugin.getDataFolder().getAbsolutePath() + "database.db");
		  }
		  catch ( Exception e) { 
		    e.printStackTrace();
		  }
		  return conn;
		}
	public static CachedRowSetImpl query(String query, boolean result) throws SQLException
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
				
			else
				sqlStatement.executeUpdate(query);
			conn.close();
			return crs;
			//return rs;
	}
	
	public static boolean checkTable(String table)
	{
		boolean result = false;
		ResultSet query;
		try {
			query = query("SELECT * FROM " + table, true);
			if (query != null)
				result = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
}
